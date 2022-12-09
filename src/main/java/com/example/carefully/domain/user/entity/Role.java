package com.example.carefully.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    CLASSIC("일반회원"), SILVER("유료회원1"), GOLD("유료회원2"), PLATINUM("유료회원3"), ADMIN("관리자");

    private static final String PREFIX = "ROLE_";
    private final String description;

    public String getFullName() {
        return PREFIX + name();
    }
}
