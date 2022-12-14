package com.example.carefully.domain.booking.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Degree {
    MASTER("석사"), DOCTOR("박사");

    private final String description;
}
