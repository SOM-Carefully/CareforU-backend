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

    /**
     * 현재 등급이 유료 회원 등급에 해당하는지 결과를 반환한다.
     *
     * @return 유료 회원 등급이라면 참 반환
     */
    public boolean isPaidRole() {
        return this.equals(Role.LEVEL2) || this.equals(Role.LEVEL3) || this.equals(Role.LEVEL4) || this.equals(Role.LEVEL5);
    }


    /**
     * 높은 등급은 낮은 등급의 게시판을 조회할 수 있으며, 반대는 안되는 조건을 검증한다.
     *
     * @param userRank 로그인 유저의 등급 랭크
     * @return 로그인 유저의 등급보다 게시판의 등급이 더 높으면 참 반환
     */
    public boolean canAccess(int userRank) {
        return rank <= userRank;
    }
}
