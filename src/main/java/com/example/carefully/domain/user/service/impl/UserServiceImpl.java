package com.example.carefully.domain.user.service.impl;

import com.example.carefully.domain.membership.entity.Membership;
import com.example.carefully.domain.membership.repository.MembershipRepository;
import com.example.carefully.domain.user.dto.TokenResponse;
import com.example.carefully.domain.user.dto.UserDto;
import com.example.carefully.domain.user.entity.User;
import com.example.carefully.domain.user.exception.DuplicatedUsernameException;
import com.example.carefully.domain.user.exception.NotValidationPasswordException;
import com.example.carefully.domain.user.exception.NotValidationRoleException;
import com.example.carefully.domain.user.repository.UserRepository;
import com.example.carefully.domain.user.service.UserService;
import com.example.carefully.global.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.carefully.global.utils.UserUtils.getCurrentUser;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /*
    로그인
     */
    @Override
    @Transactional
    public TokenResponse login(UserDto.LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken unauthenticated = UsernamePasswordAuthenticationToken.unauthenticated(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(unauthenticated);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);

        return new TokenResponse(jwt);
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
        Membership membership = Membership.request(user, registerRequest.getContent());

        saveUserAndMembership(user, membership);
    }

    @Override
    @Transactional
    public void adminSignup(UserDto.AdminRegisterRequest registerRequest) {
        isDuplicateUsername(registerRequest.getUsername());
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        User user = User.adminRequest(registerRequest);
        Membership membership = Membership.request(user, registerRequest.getContent());

        saveUserAndMembership(user, membership);
    }

    @Transactional
    public void saveUserAndMembership(User user, Membership membership) {
        userRepository.save(user);
        membershipRepository.save(membership);
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


    /*
    회원 탈퇴
     */
    @Override
    @Transactional
    public void signout(UserDto.SignoutRequest signoutRequest) {

        User currentUser = getCurrentUser(userRepository);

        UsernamePasswordAuthenticationToken unauthenticated = passwordCheckLogic(currentUser, signoutRequest.getPassword());

        if (unauthenticated != null) {
            userRepository.delete(currentUser);
        } else {
            throw new NotValidationPasswordException();
        }
    }

    /*
    로그인한 일반 사용자 정보 조회
     */
    @Override
    @Transactional(readOnly = true)
    public UserDto.UserResponse getMyUserWithAuthorities() {
        User user = getCurrentUser(userRepository);
        return UserDto.UserResponse.create(user);
    }

    /*
    로그인한 일반 사용자 정보 조회
     */
    @Override
    @Transactional(readOnly = true)
    public UserDto.AdminResponse getMyAdminWithAuthorities() {
        User user = getCurrentUser(userRepository);
        return UserDto.AdminResponse.create(user);
    }

    @Transactional(readOnly = true)
    public UsernamePasswordAuthenticationToken passwordCheckLogic(User user, String password) {
        UsernamePasswordAuthenticationToken unauthenticated = UsernamePasswordAuthenticationToken.unauthenticated(
                user.getUsername(),
                password
        );
        return unauthenticated;
    }

}
