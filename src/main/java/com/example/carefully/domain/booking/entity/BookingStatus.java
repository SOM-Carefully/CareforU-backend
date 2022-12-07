package com.example.carefully.domain.booking.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookingStatus {
    ACCEPT("승인"), WAITING("대기"), CANCEL("거절"), COMPLETE("완료");

    private final String description;
}