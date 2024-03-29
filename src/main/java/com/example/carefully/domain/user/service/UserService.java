package com.example.carefully.domain.user.service;

import com.example.carefully.domain.user.dto.TokenResponse;
import com.example.carefully.domain.user.dto.UserDto;
import com.example.carefully.global.dto.SliceDto;


public interface UserService {
    TokenResponse.TokenInfo login(UserDto.LoginRequest loginRequest);
    void logout(UserDto.Logout logout);

    void userSignup(UserDto.UserRegisterRequest registerRequest);
    void adminSignup(UserDto.AdminRegisterRequest registerRequest);
    void signout(UserDto.SignoutRequest signoutRequest);
    void forceSignout(String username);
    void userUpdate(UserDto.UserUpdateRequest userUpdateRequest);
    void userRoleUpdate(String username, String role);
    void adminUpdate(UserDto.AdminUpdateRequest adminUpdateRequest);
    void passwordUpdate(UserDto.updatePasswordRequest updatePasswordRequest);

    UserDto.UserResponse getMyUserWithAuthorities();
    UserDto.AdminResponse getMyAdminWithAuthorities();
    UserDto.UserResponse getUserWithAuthorities(String username);

    void adminSignupTest(UserDto.AdminRegisterRequest registerRequest);

    SliceDto<UserDto.UserAllResponse> userAllLookup();
}