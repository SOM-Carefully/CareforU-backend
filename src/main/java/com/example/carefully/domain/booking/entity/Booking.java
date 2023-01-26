package com.example.carefully.domain.booking.entity;

import com.example.carefully.domain.booking.dto.BookingDto;
import com.example.carefully.domain.bookingRequest.entity.BookingStatus;
import com.example.carefully.domain.bookingRequest.entity.BusinessType;
import com.example.carefully.domain.user.entity.User;
import com.example.carefully.global.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@DiscriminatorValue("Booking")
@Getter
@NoArgsConstructor(access = PROTECTED)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="Booking_Type")
public abstract class Booking extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    public Booking(Long id) {
        this.id = id;
    }
}