package com.example.carefully.domain.user.controller;

import com.example.carefully.domain.user.dto.RegisterRequest;
import com.example.carefully.global.dto.BaseResponse;
import com.example.carefully.domain.user.dto.LoginRequest;
import com.example.carefully.domain.user.dto.TokenResponse;
import com.example.carefully.domain.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.example.carefully.domain.user.dto.UserResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Api(tags = {"유저 관련 API"})
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "로그인", notes = "로그인 API")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<TokenResponse>> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(BaseResponse.create(LOGIN_SUCCESS.getMessage(), userService.login(request)));
    }

    @ApiOperation(value = "회원가입", notes = "회원가입 API")
    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<RegisterRequest>> signup(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(BaseResponse.create(REGISTER_SUCCESS.getMessage(), userService.signup(registerRequest)));
    }
}