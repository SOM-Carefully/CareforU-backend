package com.example.carefully.domain.booking.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="Education")
@DiscriminatorValue("Education")
public class Education extends Booking{
    private Degree degree;
    private EducationContent educationContent;
}

@Getter
@RequiredArgsConstructor
enum Degree {
    MASTER("석사"), DOCTOR("박사");

    private final String description;
}

@Getter
@RequiredArgsConstructor
enum EducationContent {
    CONSULTING("컨설팅"), CORRECTION("교정/교열"), TRANSLATION("번역");

    private final String description;
}
