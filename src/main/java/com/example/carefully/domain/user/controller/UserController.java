package com.example.carefully.domain.user.controller;

import com.example.carefully.domain.user.dto.UserDto;
import com.example.carefully.domain.user.service.impl.UserServiceImpl;
import com.example.carefully.global.dto.BaseResponse;
import com.example.carefully.domain.user.dto.TokenResponse;
import com.example.carefully.domain.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.example.carefully.domain.user.dto.UserResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Api(tags = {"유저 관련 API"})
public class UserController {
    private final UserServiceImpl userService;

    @ApiOperation(value = "로그인", notes = "로그인 API")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<TokenResponse>> login(@RequestBody UserDto.LoginRequest request) {
        return ResponseEntity.ok(BaseResponse.create(LOGIN_SUCCESS.getMessage(), userService.login(request)));
    }

    @ApiOperation(value = "일반회원 회원가입 신청", notes = "회원가입 API")
    @PostMapping("/user/signup")
    public ResponseEntity signup(@RequestBody UserDto.UserRegisterRequest registerRequest) {
        userService.userSignup(registerRequest);
        return ResponseEntity.ok(BaseResponse.create(REGISTER_REQUEST_SUCCESS.getMessage()));
    }

    @ApiOperation(value = "어드민 회원가입 신청", notes = "회원가입 API")
    @PostMapping("/admin/signup")
    public ResponseEntity signup(@RequestBody UserDto.AdminRegisterRequest registerRequest) {
        userService.adminSignup(registerRequest);
        return ResponseEntity.ok(BaseResponse.create(REGISTER_REQUEST_SUCCESS.getMessage()));
    }

    @ApiOperation(value = "일반 유저 내 정보 조회", notes = "회원 조회 API")
    @GetMapping("/user/my")
    @PreAuthorize("hasAnyRole('CLASSIC','SILVER', 'GOLD', 'PLATINUM')")
    public ResponseEntity<BaseResponse<UserDto.UserResponse>> getMyUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(BaseResponse.create(MY_LOOKUP_SUCCESS.getMessage(), userService.getMyUserWithAuthorities()));
    }
    @ApiOperation(value = "어드민 내 정보 조회", notes = "회원 조회 API")
    @GetMapping("/admin/my")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<BaseResponse<UserDto.AdminResponse>> getMyAdminInfo(HttpServletRequest request) {
        return ResponseEntity.ok(BaseResponse.create(MY_LOOKUP_SUCCESS.getMessage(), userService.getMyAdminWithAuthorities()));
    }


//    @ApiOperation(value = "내 정보 수정", notes = "회원 수정 API")
//    @PutMapping("/my")
//    @PreAuthorize("hasAnyRole('GENERAL','OPERATION', 'ADMIN')")
//    public ResponseEntity update(@RequestBody UserDto.UpdateRequest updateRequest) {
//        userService.update(updateRequest);
//        return ResponseEntity.ok(BaseResponse.create(UPDATE_SUCCESS.getMessage()));
//    }

    @ApiOperation(value = "회원 탈퇴", notes = "회원 탈퇴 API")
    @PostMapping("/signout")
    @PreAuthorize("hasAnyRole('GENERAL','OPERATION', 'ADMIN')")
    public ResponseEntity signout(@RequestBody UserDto.SignoutRequest signoutRequest) {
        userService.signout(signoutRequest);
        return ResponseEntity.ok(BaseResponse.create(SIGNOUT_SUCCESS.getMessage()));
    }

//    @GetMapping("/users/{username}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    public ResponseEntity<BaseResponse<UserDto.RegisterRequest>> getUserInfo(@PathVariable String username) {
//        return ResponseEntity.ok(BaseResponse.create(USER_LOOKUP_SUCCESS.getMessage(), userService.getUserWithAuthorities(username)));
//    }
}