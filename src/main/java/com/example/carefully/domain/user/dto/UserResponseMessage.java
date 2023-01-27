package com.example.carefully.domain.user.dto;

import lombok.Getter;

@Getter
public enum UserResponseMessage {
    LOGIN_SUCCESS("로그인에 성공하였습니다."),
    LOGOUT_SUCCESS("로그아웃에 성공하였습니다."),
    USERNAME_EXIST("이미 등록된 회원의 아이디입니다."),
    REGISTER_REQUEST_SUCCESS("회원가입 신청에 성공하였습니다."),
    MY_LOOKUP_SUCCESS("내 정보 조회에 성공하였습니다."),
    UPDATE_SUCCESS("회원 정보 수정에 성공하였습니다."), 
    USER_LOOKUP_SUCCESS("회원 정보 조회에 성공하였습니다."),
    REGISTER_SUCCESS("회원가입에 성공하였습니다."),
    SIGNOUT_SUCCESS("회원 탈퇴에 성공하였습니다."),
    SEND_SUCCESS("메세지 전송에 성공하였습니다."),
    VALIDATION_SUCCESS("본인인증에 성공하였습니다.")
    ;


    private final String message;

    UserResponseMessage(String message) {
        this.message = message;
    }
}
