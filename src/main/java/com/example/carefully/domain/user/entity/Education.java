package com.example.carefully.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Education {
    UNDERGRADUATE("학부생"), BACHELOR ("학사"), MASTER("석사"), DOCTOR("박사");

    private final String description;
}
