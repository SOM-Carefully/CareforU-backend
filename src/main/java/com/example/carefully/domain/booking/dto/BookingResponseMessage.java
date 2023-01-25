package com.example.carefully.domain.booking.dto;

import lombok.Getter;

@Getter
public enum BookingResponseMessage {
    REQUEST_SUCCESS("서비스 신청이 완료되었습니다."),
    LOOKUP_SUCCESS("서비스 조회에 성공하였습니다."),
    UPDATE_SUCCESS("서비스 수정 성공하였습니다."),
    ACCEPT_SUCCESS("서비스 수락에 성공하였습니다."),
    CANCEL_SUCCESS("서비스 취소에 성공하였습니다."),
    ONGOING_SUCCESS("서비스 진행 중 처리에 성공하였습니다."),
    COMPLETE_SUCCESS("서비스가 완료되었습니다.");

    private final String message;

    BookingResponseMessage(String message) {
        this.message = message;
    }
}
