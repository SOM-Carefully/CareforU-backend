package com.example.carefully.domain.user.controller;

import com.example.carefully.domain.booking.dto.BookingDto;
import com.example.carefully.domain.user.dto.UserDto;
import com.example.carefully.domain.user.service.impl.UserServiceImpl;
import com.example.carefully.global.dto.BaseResponse;
import com.example.carefully.domain.user.dto.TokenResponse;
import com.example.carefully.domain.user.service.UserService;
import com.example.carefully.global.dto.SliceDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.example.carefully.domain.booking.dto.BookingResponseMessage.LOOKUP_SUCCESS;
import static com.example.carefully.domain.user.dto.UserResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Api(tags = {"유저 관련 API"})
public class UserController {
    private final UserServiceImpl userService;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인에 성공하였습니다."),
            @ApiResponse(responseCode = "500", description = "존재하지 않는 이메일이거나 비밀번호일 경우 발생할 수 있습니다.")
    })
    @ApiOperation(value = "로그인", notes = "로그인을 합니다.")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<TokenResponse>> login(@Valid @RequestBody UserDto.LoginRequest request) {
        return ResponseEntity.ok(BaseResponse.create(LOGIN_SUCCESS.getMessage(), userService.login(request)));
    }


    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 신청에 성공하였습니다."),
            @ApiResponse(responseCode = "500", description = "이미 존재하는 이메일, 주민번호, 전화번호이거나 활성회되지 않은 회원의 경우 발생할 수 있습니다."),
    })
    @ApiOperation(value = "일반 유저 회원가입 신청", notes = "일반유저가 회원가입을 신청합니다.")
    @PostMapping("/users/signup")
    public ResponseEntity userSignup(@Valid @RequestBody UserDto.UserRegisterRequest registerRequest) {
        userService.userSignup(registerRequest);
        return ResponseEntity.ok(BaseResponse.create(REGISTER_REQUEST_SUCCESS.getMessage()));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 신청에 성공하였습니다."),
            @ApiResponse(responseCode = "500", description = "이미 존재하는 이메일, 주민번호, 전화번호이거나 활성회되지 않은 회원의 경우 발생할 수 있습니다."),
    })
    @ApiOperation(value = "어드민 회원가입 신청", notes = "어드민 회원이 회원가입을 신청합니다.")
    @PostMapping("/admins/signup")
    public ResponseEntity adminSignup(@Valid @RequestBody UserDto.AdminRegisterRequest registerRequest) {
        userService.adminSignup(registerRequest);
        return ResponseEntity.ok(BaseResponse.create(REGISTER_REQUEST_SUCCESS.getMessage()));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입에 성공하였습니다."),
            @ApiResponse(responseCode = "500", description = "이미 존재하는 회원이거나, 활성회되지 않은 회원의 경우 발생할 수 있습니다."),
    })
    @ApiOperation(value = "어드민 회원가입", notes = "신청 없이 바로 어드민 회원가입이 가능합니다.")
    @PostMapping("/admins/signup/test")
    public ResponseEntity adminTestSignup(@RequestBody UserDto.AdminRegisterRequest registerRequest) {
        userService.adminSignupTest(registerRequest);
        return ResponseEntity.ok(BaseResponse.create(REGISTER_SUCCESS.getMessage()));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "내 정보 조회에 성공하였습니다."),
            @ApiResponse(responseCode = "500", description = "로그인이 안 되어 있거나 활성회되지 않은 회원의 경우 발생할 수 있습니다.."),
            @ApiResponse(responseCode = "401", description = "권한이 없는 유저가 접근했을 경우 발생할 수 있습니다.")
    })
    @ApiOperation(value = "일반 유저 내 정보 조회", notes = "일반 유저가 자신의 정보를 조회합니다.")
    @GetMapping("/users/my")
    @PreAuthorize("hasAnyRole('CLASSIC','SILVER', 'GOLD', 'PLATINUM')")
    public ResponseEntity<BaseResponse<UserDto.UserResponse>> getMyUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(BaseResponse.create(MY_LOOKUP_SUCCESS.getMessage(), userService.getMyUserWithAuthorities()));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "내 정보 조회에 성공하였습니다."),
            @ApiResponse(responseCode = "500", description = "로그인이 안 되어 있거나 활성회되지 않은 회원의 경우 발생할 수 있습니다.."),
            @ApiResponse(responseCode = "401", description = "권한이 없는 유저가 접근했을 경우 발생할 수 있습니다.")
    })
    @ApiOperation(value = "어드민 내 정보 조회", notes = "어드민 회원이 자신의 정보를 조회합니다.")
    @GetMapping("/admins/my")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<BaseResponse<UserDto.AdminResponse>> getMyAdminInfo(HttpServletRequest request) {
        return ResponseEntity.ok(BaseResponse.create(MY_LOOKUP_SUCCESS.getMessage(), userService.getMyAdminWithAuthorities()));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 정보 수정에 성공하였습니다."),
            @ApiResponse(responseCode = "500", description = "로그인이 안 되어 있거나 활성회되지 않은 회원의 경우 발생할 수 있습니다.."),
            @ApiResponse(responseCode = "401", description = "권한이 없는 유저가 접근했을 경우 발생할 수 있습니다.")
    })
    @ApiOperation(value = "일반 유저 내 정보 수정", notes = "일반 유저의 정보를 수정합니다.")
    @PatchMapping("/users/my")
    @PreAuthorize("hasAnyRole('CLASSIC','SILVER', 'GOLD', 'PLATINUM')")
    public ResponseEntity userUpdate(@Valid @RequestBody UserDto.UserUpdateRequest userUpdateRequest) {
        userService.userUpdate(userUpdateRequest);
        return ResponseEntity.ok(BaseResponse.create(UPDATE_SUCCESS.getMessage()));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 정보 수정에 성공하였습니다."),
            @ApiResponse(responseCode = "500", description = "로그인이 안 되어 있거나 활성회되지 않은 회원의 경우 발생할 수 있습니다.."),
            @ApiResponse(responseCode = "401", description = "권한이 없는 유저가 접근했을 경우 발생할 수 있습니다.")
    })
    @ApiOperation(value = "일반 유저 등급 수정", notes = "어드민이 일반회원의 등급(CLASSIC/SILVER/GOLD/PLATINUM)을 수정합니다.")
    @PatchMapping("/users/{role}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity userUpdate(@RequestParam("username") String username, @PathVariable String role) {
        userService.userRoleUpdate(username, role);
        return ResponseEntity.ok(BaseResponse.create(UPDATE_SUCCESS.getMessage()));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 정보 수정에 성공하였습니다."),
            @ApiResponse(responseCode = "500", description = "로그인이 안 되어 있거나 활성회되지 않은 회원의 경우 발생할 수 있습니다.."),
            @ApiResponse(responseCode = "401", description = "권한이 없는 유저가 접근했을 경우 발생할 수 있습니다.")
    })
    @ApiOperation(value = "어드민 유저 내 정보 수정", notes = "어드민 회원의 정보를 수정합니다.")
    @PatchMapping("/admins/my")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity adminUpdate(@Valid @RequestBody UserDto.AdminUpdateRequest adminUpdateRequest) {
        userService.adminUpdate(adminUpdateRequest);
        return ResponseEntity.ok(BaseResponse.create(UPDATE_SUCCESS.getMessage()));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 탈퇴에 성공하였습니다."),
            @ApiResponse(responseCode = "500", description = "로그인이 안 되어 있거나 활성회되지 않은 회원의 경우 발생할 수 있습니다.."),
            @ApiResponse(responseCode = "401", description = "권한이 없는 유저가 접근했을 경우 발생할 수 있습니다.")
    })
    @ApiOperation(value = "회원 탈퇴", notes = "로그인한 유저가 회원 탈퇴를 진행합니다.")
    @PostMapping("/sign-out")
    public ResponseEntity signout(@Valid @RequestBody UserDto.SignoutRequest signoutRequest) {
        userService.signout(signoutRequest);
        return ResponseEntity.ok(BaseResponse.create(SIGNOUT_SUCCESS.getMessage()));
    }


    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 탈퇴에 성공하였습니다."),
            @ApiResponse(responseCode = "500", description = "로그인이 안 되어 있거나 활성회되지 않은 회원의 경우 발생할 수 있습니다.."),
            @ApiResponse(responseCode = "401", description = "권한이 없는 유저가 접근했을 경우 발생할 수 있습니다.")
    })
    @ApiOperation(value = "강제 탈퇴", notes = "어드민이 강제 회원 탈퇴를 진행합니다.")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/forced-withdrawal")
    public ResponseEntity forceSignout(@RequestParam("username") String username) {
        userService.forceSignout(username);
        return ResponseEntity.ok(BaseResponse.create(SIGNOUT_SUCCESS.getMessage()));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 정보 조회에 성공하였습니다."),
            @ApiResponse(responseCode = "500", description = "로그인이 안 되어 있거나 활성회되지 않은 회원의 경우 발생할 수 있습니다.."),
            @ApiResponse(responseCode = "401", description = "권한이 없는 유저가 접근했을 경우 발생할 수 있습니다.")
    })
    @ApiOperation(value = "모든 회원 조회", notes = "어드민이 모든 회원의 정보를 조회합니다.")
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<BaseResponse<SliceDto<UserDto.UserAllResponse>>> userAllLookuo() {
        return ResponseEntity.ok(BaseResponse.create(USER_LOOKUP_SUCCESS.getMessage(), userService.userAllLookup()));    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 정보 조회에 성공하였습니다."),
            @ApiResponse(responseCode = "500", description = "로그인이 안 되어 있거나 활성회되지 않은 회원의 경우 발생할 수 있습니다.."),
            @ApiResponse(responseCode = "401", description = "권한이 없는 유저가 접근했을 경우 발생할 수 있습니다.")
    })
    @ApiOperation(value = "회원 정보 조회", notes = "어드민이 이메일을 통해 회원의 정보를 조회합니다.")
    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<BaseResponse<UserDto.UserResponse>> getUserInfo(@RequestParam("username") String username) {
        return ResponseEntity.ok(BaseResponse.create(USER_LOOKUP_SUCCESS.getMessage(), userService.getUserWithAuthorities(username)));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 정보 수정에 성공하였습니다."),
            @ApiResponse(responseCode = "500", description = "로그인이 안 되어 있거나 활성회되지 않은 회원의 경우 발생할 수 있습니다.."),
            @ApiResponse(responseCode = "401", description = "권한이 없는 유저가 접근했을 경우 발생할 수 있습니다.")
    })
    @ApiOperation(value = "비밀번호 변경", notes = "로그인된 회원의 비밀번호를 변경합니다.")
    @PatchMapping("/change-password")
    @PreAuthorize("hasAnyRole('CLASSIC','SILVER', 'GOLD', 'PLATINUM', 'ADMIN')")
    public ResponseEntity passwordUpdate(@Valid @RequestBody UserDto.updatePasswordRequest updatePasswordRequest) {
        userService.passwordUpdate(updatePasswordRequest);
        return ResponseEntity.ok(BaseResponse.create(UPDATE_SUCCESS.getMessage()));
    }
}