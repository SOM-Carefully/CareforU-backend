package com.example.carefully.domain.user.service;

import com.example.carefully.domain.user.dto.LoginRequest;
import com.example.carefully.domain.user.dto.RegisterRequest;
import com.example.carefully.domain.user.dto.TokenResponse;
import com.example.carefully.domain.user.entity.Operation;
import com.example.carefully.domain.user.entity.Role;
import com.example.carefully.domain.user.exception.DuplicatedUsernameException;
import com.example.carefully.domain.user.exception.NotValidationRoleException;
import com.example.carefully.domain.user.repository.CommonUserRepository;
import com.example.carefully.global.security.jwt.TokenProvider;
import com.example.carefully.domain.user.entity.User;
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
    private final CommonUserRepository commonUserRepository;
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

        String requestRole = String.valueOf(registerRequest.getRole());

        if (requestRole.equals("USER")) {
            User user = User.builder()
                    .username(registerRequest.getUsername())
                    .name(registerRequest.getName())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .foreignerNumber(registerRequest.getForeignerNumber())
                    .phoneNumber(registerRequest.getPhoneNumber())
                    .gender(registerRequest.getGender())
                    .address(registerRequest.getAddress())
                    .university(registerRequest.getUniversity())
                    .major(registerRequest.getMajor())
                    .role(Role.valueOf(registerRequest.getRole().name()))
                    .activated(true)
                    .build();
            return RegisterRequest.fromUser(commonUserRepository.save(user));
        }

        else if (requestRole.equals("OPERATION")) {
            Operation operation = Operation.builder()
                    .username(registerRequest.getUsername())
                    .name(registerRequest.getName())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .phoneNumber(registerRequest.getPhoneNumber())
                    .businessRegisterNumber(registerRequest.getBusinessRegisterNumber())
                    .businessType(registerRequest.getBusinessType())
                    .businessName(registerRequest.getBusinessName())
                    .role(Role.valueOf(registerRequest.getRole().name()))
                    .activated(true)
                    .build();
            return RegisterRequest.fromOperation(commonUserRepository.save(operation));
        }

        else {
            throw new NotValidationRoleException();
        }
    }

    private boolean isDuplicateUsername(String username) {
        return commonUserRepository.existsByUsername(username);
    }
}