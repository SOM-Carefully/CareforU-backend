package com.example.carefully.domain.membership.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MembershipStatus {
    ACCEPT("승인"), WAITING ("대기"), REJECT("거절");

    private final String membershipStatus;
}