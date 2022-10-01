package com.example.carefully.domain.user.service;

import com.example.carefully.domain.user.dto.LoginRequest;
import com.example.carefully.domain.user.dto.RegisterRequest;
import com.example.carefully.domain.user.dto.TokenResponse;
import com.example.carefully.domain.user.entity.Role;
import com.example.carefully.domain.user.exception.DuplicatedUsernameException;
import com.example.carefully.global.security.jwt.TokenProvider;
import com.example.carefully.domain.user.entity.User;
import com.example.carefully.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public TokenResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken unauthenticated = UsernamePasswordAuthenticationToken.unauthenticated(
            loginRequest.getUsername(),
            loginRequest.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(unauthenticated);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        return new TokenResponse(jwt);
    }

    @Transactional
    public RegisterRequest signup(RegisterRequest registerRequest) {
        if (isDuplicateUsername(registerRequest.getUsername())) {
            throw new DuplicatedUsernameException();
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .name(registerRequest.getName())
                .phoneNumber(registerRequest.getPhoneNumber())
                .gender(registerRequest.getGender())
                .address(registerRequest.getAddress())
                .university(registerRequest.getUniversity())
                .role(Role.valueOf(registerRequest.getRole().name()))
                .activated(true)
                .build();

        return RegisterRequest.from(userRepository.save(user));
    }

    private boolean isDuplicateUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}