package com.example.carefully.domain.booking.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="Dwelling")
@DiscriminatorValue("Dwelling")
public class Dwelling extends Booking {
    private TransactionMethod transactionMethod;
    private NumberOfRooms numberOfRooms;
}

@Getter
@RequiredArgsConstructor
enum TransactionMethod {
    CHARTER("전세"), MONTHLY("월세");

    private final String description;
}

@Getter
@RequiredArgsConstructor
enum NumberOfRooms {
    ONEROOM("원룸"), TWOROOM("투룸"), TWOBAY("투베이");

    private final String description;
}