package com.example.carefully.domain.booking.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NumberOfRooms {
    ONEROOM("원룸"), TWOROOM("투룸"), TWOBAY("투베이");

    private final String description;
}
