package com.example.carefully.domain.user.service.impl;

import com.example.carefully.domain.user.dto.TokenResponse;
import com.example.carefully.domain.user.dto.UserDto;
import com.example.carefully.domain.user.entity.CommonUser;
import com.example.carefully.domain.user.entity.Operation;
import com.example.carefully.domain.user.entity.User;
import com.example.carefully.domain.user.exception.DuplicatedUsernameException;
import com.example.carefully.domain.user.exception.NotValidationRoleException;
import com.example.carefully.domain.user.repository.CommonUserRepository;
import com.example.carefully.domain.user.service.UserService;
import com.example.carefully.global.security.jwt.TokenProvider;
import com.example.carefully.global.security.util.SecurityUtil;
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
public class UserServiceImpl implements UserService {

    private final CommonUserRepository commonUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

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

    @Override
    @Transactional
    public UserDto.RegisterRequest signup(UserDto.RegisterRequest registerRequest) {
        if (isDuplicateUsername(registerRequest.getUsername())) {
            throw new DuplicatedUsernameException();
        }

        String requestRole = String.valueOf(registerRequest.getRole());

        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        if (requestRole.equals("USER")) {
            User user = User.registerUser(registerRequest);
            return UserDto.RegisterRequest.fromUser(commonUserRepository.save(user));
        }
        else if (requestRole.equals("OPERATION")) {
            Operation operation = Operation.registerOperation(registerRequest);
            return UserDto.RegisterRequest.fromOperation(commonUserRepository.save(operation));
        }
        else {
            throw new NotValidationRoleException();
        }
    }

    @Override
    @Transactional
    public UserDto.UpdateRequest update(UserDto.UpdateRequest updateRequest) {

        CommonUser commonUser = getCurrentUser();

        if (commonUser.getRole().name().equals("USER")) {
            User user = (User) commonUser;
            User result = User.updateUser(user, updateRequest);
            user.update(result);
            return UserDto.UpdateRequest.fromUser(commonUserRepository.save(result));
        }
        else if (commonUser.getRole().name().equals("OPERATION")) {
            Operation operation = (Operation) commonUser;
            Operation result = Operation.updateOperation(operation, updateRequest);
            operation.update(result);
            return UserDto.UpdateRequest.fromOperation(commonUserRepository.save(operation));
        }
        else {
            throw new NotValidationRoleException();
        }
    }

    @Override
    public boolean isDuplicateUsername(String username) {
        return commonUserRepository.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto.RegisterRequest getUserWithAuthorities(String username) {

        CommonUser commonUser =  getCurrentUser();
        String requestRole = commonUser.getRole().name();

        if (requestRole.equals("USER")) {
            return UserDto.RegisterRequest.fromUser((User)commonUserRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
        }
        else if (requestRole.equals("OPERATION")) {
            return UserDto.RegisterRequest.fromOperation((Operation)commonUserRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
        }
        else {
            throw new NotValidationRoleException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto.RegisterRequest getMyUserWithAuthorities() {

        CommonUser commonUser =  getCurrentUser();
        String requestRole = commonUser.getRole().name();

        if (requestRole.equals("USER")) {
            return UserDto.RegisterRequest.fromUser((User) commonUser);
        } else if (requestRole.equals("OPERATION")) {
            return UserDto.RegisterRequest.fromOperation((Operation) commonUser);
        } else {
            throw new NotValidationRoleException();
        }
    }

    @Transactional(readOnly = true)
    public CommonUser getCurrentUser() {
        return SecurityUtil.getCurrentUsername()
                .flatMap(commonUserRepository::findOneWithAuthoritiesByUsername)
                .orElseThrow(() -> new NotValidationRoleException());
    }
}