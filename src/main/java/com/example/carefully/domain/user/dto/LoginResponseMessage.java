package com.example.carefully.domain.user.dto;

import lombok.Getter;

@Getter
public enum LoginResponseMessage {
    LOGIN_SUCCESS("로그인에 성공하였습니다.");

    private final String message;

    LoginResponseMessage(String message) {
        this.message = message;
    }
}