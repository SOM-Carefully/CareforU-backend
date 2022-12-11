package com.example.carefully.domain.user.service;

import com.example.carefully.domain.user.dto.TokenResponse;
import com.example.carefully.domain.user.dto.UserDto;


public interface UserService {
    TokenResponse login(UserDto.LoginRequest loginRequest);

    void userSignup(UserDto.UserRegisterRequest registerRequest);
    void adminSignup(UserDto.AdminRegisterRequest registerRequest);
    void signout(UserDto.SignoutRequest signoutRequest);
    void userUpdate(UserDto.UserUpdateRequest userUpdateRequest);
    void adminUpdate(UserDto.AdminUpdateRequest adminUpdateRequest);

    UserDto.UserResponse getMyUserWithAuthorities();
    UserDto.AdminResponse getMyAdminWithAuthorities();

    void adminSignupTest(UserDto.AdminRegisterRequest registerRequest);

//    UserDto.RegisterRequest getUserWithAuthorities(String username);
}