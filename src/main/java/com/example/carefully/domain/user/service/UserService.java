package com.example.carefully.domain.user.service;

import com.example.carefully.domain.user.dto.TokenResponse;
import com.example.carefully.domain.user.dto.UserDto;


public interface UserService {
    TokenResponse login(UserDto.LoginRequest loginRequest);

    void userSignup(UserDto.UserRegisterRequest registerRequest);
    void adminSignup(UserDto.AdminRegisterRequest registerRequest);
    void signout(UserDto.SignoutRequest signoutRequest);

//    UserDto.UserResponse getMyUserWithAuthorities();

//    UserDto.RegisterRequest getUserWithAuthorities(String username);
}