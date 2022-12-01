package com.example.carefully.domain.booking.dto;

import lombok.Getter;

@Getter
public enum BookingResponseMessage {
    REQUEST_SUCCESS("서비스 신청이 완료되었습니다."),
    LOOKUP_SUCCESS("서비스 조회에 성공하였습니다.");

    private final String message;

    BookingResponseMessage(String message) {
        this.message = message;
    }
}
