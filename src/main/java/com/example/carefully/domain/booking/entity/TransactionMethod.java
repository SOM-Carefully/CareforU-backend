package com.example.carefully.domain.booking.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionMethod {
    CHARTER("전세"), MONTHLY("월세");

    private final String description;
}
