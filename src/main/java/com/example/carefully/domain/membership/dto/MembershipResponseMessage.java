package com.example.carefully.domain.membership.dto;

import lombok.Getter;

@Getter
public enum MembershipResponseMessage {
    LOOKUP_SUCCESS("회원가입 신청 리스트 조회에 성공하였습니다."),
    SIGNUP_SUCCESS("회원가입 승인에 성공하였습니다.")
    ;

    private final String message;

    MembershipResponseMessage(String message) {
        this.message = message;
    }
}
