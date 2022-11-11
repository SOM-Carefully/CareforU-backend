package com.example.carefully.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessType {
    TRANSLATE("번역"), DWELLING("주거"), TRAFFIC("교통");

    private static final String PREFIX = "TYPE_";
    private final String description;

    public String getFullName() {
        return PREFIX + name();
    }
}
