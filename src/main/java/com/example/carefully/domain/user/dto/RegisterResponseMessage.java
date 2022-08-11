package com.example.carefully.domain.user.dto;

import lombok.Getter;

@Getter
public enum RegisterResponseMessage {
    USERNAME_EXIST("이미 등록된 회원의 아이디입니다."),
    REGISTER_SUCCESS("회원가입에 성공하였습니다.");

    private final String message;

    RegisterResponseMessage(String message) {
        this.message = message;
    }
}
