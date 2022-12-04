package com.example.carefully.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    GENERAL("일반"), OPERATION("운영"), ADMIN("관리자");

    private static final String PREFIX = "ROLE_";
    private final String description;

    public String getFullName() {
        return PREFIX + name();
    }
}
