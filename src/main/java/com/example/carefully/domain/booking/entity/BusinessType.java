package com.example.carefully.domain.booking.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessType {
    COMMUNICATION("통신"), DWELLING("주거"), TRAFFIC("교통"), EDUCATION("교육");

    private static final String PREFIX = "TYPE_";
    private final String description;

    public String getFullName() {
        return PREFIX + name();
    }
}