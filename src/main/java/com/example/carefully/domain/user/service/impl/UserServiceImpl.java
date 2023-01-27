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

    /*
    로그인
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

    /*
    로그아웃
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

    /*
    회원가입 신청
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

    @Override
    @Transactional
    public void adminSignupTest(UserDto.AdminRegisterRequest registerRequest) {
        isDuplicateUsername(registerRequest.getUsername());
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        User user = User.adminRequest(registerRequest);
        user.signup();

        userRepository.save(user);
    }

    /*
    유저 아이디 중복검사
     */
    public void isDuplicateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new DuplicatedUsernameException();
        }
    }

    /*
    회원 정보 수정
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

    @Override
    @Transactional
    public void adminUpdate(UserDto.AdminUpdateRequest adminUpdateRequest) {
        User currentUser = getCurrentUser(userRepository);
        currentUser.updateAdmin(adminUpdateRequest.getProfileUrl(), adminUpdateRequest.getNickname(), adminUpdateRequest.getBio());
        userRepository.save(currentUser);
    }

    @Override
    @Transactional
    public void userRoleUpdate(String username, String role) {
        User user = userRepository.findOneWithAuthoritiesByUsername(username).orElseThrow(NotFoundUserException::new);
        user.updateUserRole(role);
        userRepository.save(user);
    }


    /*
    회원 탈퇴
     */
    @Override
    @Transactional
    public void signout(UserDto.SignoutRequest signoutRequest) {
        User currentUser = getCurrentUser(userRepository);
        currentUser = passwordCheckLogic(currentUser.getId(), signoutRequest.getPassword());
        userRepository.delete(currentUser);
    }

    /*
    강제 회원 탈퇴
    */
    @Override
    @Transactional
    public void forceSignout(String username) {
        User user = userRepository.findOneWithAuthoritiesByUsername(username).orElseThrow(NotFoundUserException::new);
        userRepository.delete(user);
    }

    /*
    로그인한 일반 사용자 정보 조회
     */
    @Override
    @Transactional(readOnly = true)
    public UserDto.UserResponse getMyUserWithAuthorities() {
        User currentUser = getCurrentUser(userRepository);
        return UserDto.UserResponse.create(currentUser);
    }

    /*
    로그인한 어드민 사용자 정보 조회
     */
    @Override
    public UserDto.AdminResponse getMyAdminWithAuthorities() {
        User currentUser = getCurrentUser(userRepository);
        return UserDto.AdminResponse.create(currentUser);
    }

    /*
    유저 이메일로 사용자 정보 조회
     */
    @Override
    public UserDto.UserResponse getUserWithAuthorities(String username) {
        User user = userRepository.findOneWithAuthoritiesByUsername(username).orElseThrow(NotFoundUserException::new);
        return UserDto.UserResponse.create(user);
    }

    /*
    전체 회원 조회
     */
    @Override
    public SliceDto<UserDto.UserAllResponse> userAllLookup() {
        Slice<User> userList = userRepository.findAllByActivatedTrueOrderByCreatedAtDesc();
        return SliceDto.create(userList.map(UserDto.UserAllResponse::create));
    }

    /*
    비밀번호 변경
     */
    @Override
    @Transactional
    public void passwordUpdate(UserDto.updatePasswordRequest updatePasswordRequest) {
        User currentUser = getCurrentUser(userRepository);
        passwordCheckLogic(currentUser.getId(), updatePasswordRequest.getOldPassword());
        currentUser.updatePassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
        userRepository.save(currentUser);
    }


    /*
    비밀번호 검증 로직
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