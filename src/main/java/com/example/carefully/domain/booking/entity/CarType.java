package com.example.carefully.domain.booking.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CarType {
    COMPACT("소형"), MIDSIZE("중형"), SUV("SUV");

    private final String description;
}
