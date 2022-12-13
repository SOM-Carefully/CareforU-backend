package com.example.carefully.domain.booking.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="Communication")
@DiscriminatorValue("Communication")
public class Communication extends Booking {
    private String modelName;
    private boolean usim;
}

