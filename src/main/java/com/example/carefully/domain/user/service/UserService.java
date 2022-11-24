package com.example.carefully.domain.user.service;

import com.example.carefully.domain.user.dto.TokenResponse;
import com.example.carefully.domain.user.dto.UserDto;


public interface UserService {
    TokenResponse login(UserDto.LoginRequest loginRequest);

    UserDto.RegisterRequest signup(UserDto.RegisterRequest registerRequest);
    UserDto.UpdateRequest update(UserDto.UpdateRequest updateRequest);

    boolean isDuplicateUsername(String username);

    UserDto.RegisterRequest getMyUserWithAuthorities();
    UserDto.RegisterRequest getUserWithAuthorities(String username);
}