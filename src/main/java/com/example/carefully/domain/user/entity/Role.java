package com.example.carefully.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
@RequiredArgsConstructor
public enum Role {
    CLASSIC("일반회원", 0),
    SILVER("유료회원1", 1),
    GOLD("유료회원2", 2),
    PLATINUM("유료회원3", 3),
    ADMIN("관리자", 4);

    private static final String PREFIX = "ROLE_";
    private final String description;
    private final int rank;

    public String getFullName() {
        return PREFIX + name();
    }

    public static Role of(String description) {
        return Arrays.stream(Role.values())
                .filter(x -> x.getDescription().equals(description))
                .findAny()
                .orElseThrow(NoSuchElementException::new);
    }

    public boolean isPaidRole() {
        return this.equals(Role.SILVER) || this.equals(Role.GOLD) || this.equals(Role.PLATINUM);
    }

    public boolean canAccess(int userRank) {
        return rank <= userRank;
    }
}
