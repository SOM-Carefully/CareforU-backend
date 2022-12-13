package com.example.carefully.domain.booking.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="Education")
@DiscriminatorValue("Education")
public class Traffic extends Booking{
}

@Getter
@RequiredArgsConstructor
enum CarType {
    COMPACT("소형"), MIDSIZE("중형"), SUV("SUV");

    private final String description;
}