package com.example.carefully.domain.booking.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EducationContent {
    CONSULTING("컨설팅"), CORRECTION("교정/교열"), TRANSLATION("번역");

    private final String description;
}
