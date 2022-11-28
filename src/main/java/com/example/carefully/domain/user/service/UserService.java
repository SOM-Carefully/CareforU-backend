package com.example.carefully.domain.user.service;

import com.example.carefully.domain.user.dto.TokenResponse;
import com.example.carefully.domain.user.dto.UserDto;


public interface UserService {
    TokenResponse login(UserDto.LoginRequest loginRequest);

    void signup(UserDto.RegisterRequest registerRequest);
    void update(UserDto.UpdateRequest updateRequest);
    void signout(UserDto.SignoutRequest signoutRequest);

    boolean isDuplicateUsername(String username);

    UserDto.UserResponse getMyUserWithAuthorities();

//    UserDto.RegisterRequest getUserWithAuthorities(String username);
}