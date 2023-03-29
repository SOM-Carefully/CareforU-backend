package com.example.carefully.domain.user.service.impl;

import com.example.carefully.domain.membership.entity.Membership;
import com.example.carefully.domain.membership.repository.MembershipRepository;
import com.example.carefully.domain.user.dto.TokenResponse;
import com.example.carefully.domain.user.dto.UserDto;
import com.example.carefully.domain.user.entity.User;
import com.example.carefully.domain.user.exception.DuplicatedUsernameException;
import com.example.carefully.domain.user.exception.NotFoundUserException;
import com.example.carefully.domain.user.exception.NotValidateToken;
import com.example.carefully.domain.user.exception.NotValidationPasswordException;
import com.example.carefully.domain.user.repository.UserRepository;
import com.example.carefully.domain.user.service.UserService;
import com.example.carefully.global.dto.SliceDto;
import com.example.carefully.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.concurrent.TimeUnit;

import static com.example.carefully.global.utils.UserUtils.getCurrentUser;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * 로그인한다.
     * 해당되는 유저가 없거나 활성화되지 않은 유저일 경우 error.
     * redis에 로그인한 유저의 토큰 정보를 저장한다.
     *
     * @param loginRequest 유저 이메일 및 유저 비밀번호
     * @return 토큰 형식(Bearer), 접속 token, refresh token, 토큰 유효 시간
     */
    @Override
    @Transactional
    public TokenResponse.TokenInfo login(UserDto.LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken unauthenticated = UsernamePasswordAuthenticationToken.unauthenticated(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(unauthenticated);
        TokenResponse.TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return TokenResponse.TokenInfo.create(tokenInfo);
    }

    /**
     * 로그아웃한다.
     * 유효한 토큰이 아닐 경우 error
     * redis에 로그인한 유저의 토큰 정보를 삭제한다다
     *
     * @param logout accessToken 및 refreshToken
     * @return 토큰 형식(Bearer), 접속 token, refresh token, 토큰 유효 시간
     */
    @Override
    @Transactional
    public void logout(UserDto.Logout logout) {
        if (!jwtTokenProvider.validateToken(logout.getAccessToken())) {
            throw new NotValidateToken();
        }
        Authentication authentication = jwtTokenProvider.getAuthentication(logout.getAccessToken());
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            redisTemplate.delete("RT:" + authentication.getName());
        }

        Long expiration = jwtTokenProvider.getExpiration(logout.getAccessToken());
        redisTemplate.opsForValue()
                .set(logout.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);
    }

    /**
     * 일반회원이 회원가입을 신청한다.
     * 기존에 존재하는 이메일인지 확인 후 존재한다면 error
     *
     * @param registerRequest 이메일, 비밀번호, 이름, 주민번호, 전화번호, 성별, 국적, 대학교, 전공, 지도교수 이름, 학위, 주소, 회원가입 신청 메세지
     */
    @Override
    @Transactional
    public void userSignup(UserDto.UserRegisterRequest registerRequest) {
        isDuplicateUsername(registerRequest.getUsername());
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        User user = User.userRequest(registerRequest);
        userRepository.save(user);

        Membership membership = Membership.request(user, registerRequest.getContent());
        membershipRepository.save(membership);
    }

    /**
     * 어드민 회원이 회원가입을 신청한다.
     * 기존에 존재하는 이메일인지 확인 후 존재한다면 error
     *
     * @param registerRequest 이메일, 비밀번호, 이름, 주민번호, 전화번호, 회원가입 신청 메세지
     */
    @Override
    @Transactional
    public void adminSignup(UserDto.AdminRegisterRequest registerRequest) {
        isDuplicateUsername(registerRequest.getUsername());
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        User user = User.adminRequest(registerRequest);
        userRepository.save(user);

        Membership membership = Membership.request(user, registerRequest.getContent());
        membershipRepository.save(membership);
    }

    /**
     * 어드민 회원이 회원가입한다. - 승인 X
     * 기존에 존재하는 이메일인지 확인 후 존재한다면 error
     *
     * @param registerRequest 이메일, 비밀번호, 이름, 주민번호, 전화번호, 회원가입 신청 메세지
     */
    @Override
    @Transactional
    public void adminSignupTest(UserDto.AdminRegisterRequest registerRequest) {
        isDuplicateUsername(registerRequest.getUsername());
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        User user = User.adminRequest(registerRequest);
        user.signup();

        userRepository.save(user);
    }

    /**
     * 입력된 이메일이 이미 존재하는지 검사한다. - 존재한다면 error
     *
     * @param username 이메일
     */
    public void isDuplicateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new DuplicatedUsernameException();
        }
    }

    /**
     * 로그인된 일반회원의 정보를 수정한다.
     * 접근 제한은 UserController로 제어
     *
     * @param userUpdateRequest 대학교 이름, 학과, 지도교수 이름, 학위, 주소, 프로필 이미지 주소, 닉네임, 한줄소개
     */
    @Override
    @Transactional
    public void userUpdate(UserDto.UserUpdateRequest userUpdateRequest) {
        User currentUser = getCurrentUser(userRepository);
        currentUser.updateUser(userUpdateRequest.getUniversityName(),
                userUpdateRequest.getEducationRequest().name(),
                userUpdateRequest.getMajor(), userUpdateRequest.getAdvisorName(), userUpdateRequest.getAddress(),
                userUpdateRequest.getProfileUrl(), userUpdateRequest.getNickname(), userUpdateRequest.getBio());
        userRepository.save(currentUser);
    }

    /**
     * 로그인된 어드민 회원의 정보를 수정한다.
     * 접근 제한은 UserController로 제어
     *
     * @param adminUpdateRequest 프로필 이미지 주소, 닉네임, 한줄소개
     */
    @Override
    @Transactional
    public void adminUpdate(UserDto.AdminUpdateRequest adminUpdateRequest) {
        User currentUser = getCurrentUser(userRepository);
        currentUser.updateAdmin(adminUpdateRequest.getProfileUrl(), adminUpdateRequest.getNickname(), adminUpdateRequest.getBio());
        userRepository.save(currentUser);
    }

    /**
     * 어드민이 특정 일반회원의 등급을 수정한다. - 이메일을 찾을 수 없을시 error
     * 접근 제한은 UserController로 제어
     *
     * @param username 이메일, role 등급
     */
    @Override
    @Transactional
    public void userRoleUpdate(String username, String role) {
        User user = userRepository.findOneWithAuthoritiesByUsername(username).orElseThrow(NotFoundUserException::new);
        user.updateUserRole(role);
        userRepository.save(user);
    }


    /**
     * 로그인된 유저의 정보를 삭제한다. (회원탈퇴) - 입력된 비밀번호와 일치하지 않을 경우 error
     *
     * @param signoutRequest 유저 비밀번호
     */
    @Override
    @Transactional
    public void signout(UserDto.SignoutRequest signoutRequest) {
        User currentUser = getCurrentUser(userRepository);
        currentUser = passwordCheckLogic(currentUser.getId(), signoutRequest.getPassword());
        userRepository.delete(currentUser);
    }

    /**
     * 어드민이 특정 유저의 정보를 강제로 삭제한다. (강제 회원탈퇴)
     * 접근 제한은 UserController로 제어
     */
    @Override
    @Transactional
    public void forceSignout(String username) {
        User user = userRepository.findOneWithAuthoritiesByUsername(username).orElseThrow(NotFoundUserException::new);
        userRepository.delete(user);
    }

    /**
     * 로그인된 일반회원의 정보를 조회한다.
     * 접근 제한은 UserController로 제어
     *
     * @return userResponse 이매일, 이름, 전화번호, 성별, 국적, 대학교, 전공, 지도교수 이름, 학위, 주소, 등급, 프로필 이미지 url, 닉네임, 한 줄 소개
     */
    @Override
    @Transactional(readOnly = true)
    public UserDto.UserResponse getMyUserWithAuthorities() {
        User currentUser = getCurrentUser(userRepository);
        return UserDto.UserResponse.create(currentUser);
    }

    /**
     * 로그인된 어드민 회원의 정보를 조회한다.
     * 접근 제한은 UserController로 제어
     *
     * @return adminResponse 이매일, 이름, 전화번호, 등급, 프로필 이미지 url, 닉네임, 한 줄 소개
     */
    @Override
    public UserDto.AdminResponse getMyAdminWithAuthorities() {
        User currentUser = getCurrentUser(userRepository);
        return UserDto.AdminResponse.create(currentUser);
    }

    /**
     * 어드민이 이메일을 통해 일반회원의 정보를 조회한다.
     * 접근 제한은 UserController로 제어
     *
     * @return userResponse 이메일, 이름, 전화번호, 성별, 국적, 대학교, 전공, 지도교수 이름, 학위, 주소, 등급, 프로필 이미지 url, 닉네임, 한 줄 소개
     */
    @Override
    public UserDto.UserResponse getUserWithAuthorities(String username) {
        User user = userRepository.findOneWithAuthoritiesByUsername(username).orElseThrow(NotFoundUserException::new);
        return UserDto.UserResponse.create(user);
    }

    /**
     * 어드민이 모든 일반회원 리스트를 조회한다.
     * 접근 제한은 UserController로 제어
     *
     * @return userAllResponse 이메일, 이름, 등급, 전화번호, 가입일시
     */
    @Override
    public SliceDto<UserDto.UserAllResponse> userAllLookup() {
        Slice<User> userList = userRepository.findAllByActivatedTrueOrderByCreatedAtDesc();
        return SliceDto.create(userList.map(UserDto.UserAllResponse::create));
    }

    /**
     * 로그인된 유저의 비밀번호를 변경한다. - 입력된 기존 비밀번호가 현재 비밀번호와 일치하지 않을 경우 error
     *
     * @param updatePasswordRequest 기존 비밀번호, 새로운 비밀번호
     */
    @Override
    @Transactional
    public void passwordUpdate(UserDto.updatePasswordRequest updatePasswordRequest) {
        User currentUser = getCurrentUser(userRepository);
        passwordCheckLogic(currentUser.getId(), updatePasswordRequest.getOldPassword());
        currentUser.updatePassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
        userRepository.save(currentUser);
    }


    /**
     * 유저의 비밀번호와 입력된 비밀번호가 일치하는지 검증한다. - 일치하지 않다면 error
     *
     * @param userId 유저 ID, password 입력된 비밀번호
     * @return 유저 정보
     */
    public User passwordCheckLogic(Long userId, String password) {
        final User persistUser = userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);
        if(!passwordEncoder
                .matches(password, persistUser.getPassword())) {
            throw new NotValidationPasswordException();
        }
        return persistUser;
    }
}