package com.example.carefully.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
@RequiredArgsConstructor
public enum Role {
    LEVEL1("1등급", 0),
    LEVEL2("2등급", 1),
    LEVEL3("3등급", 2),
    LEVEL4("4등급", 3),
    LEVEL5("5등급", 4),
    ADMIN("관리자", 5);

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
        return this.equals(Role.LEVEL2) || this.equals(Role.LEVEL3) || this.equals(Role.LEVEL4) || this.equals(Role.LEVEL5);
    }

    public boolean canAccess(int userRank) {
        return rank <= userRank;
    }
}
