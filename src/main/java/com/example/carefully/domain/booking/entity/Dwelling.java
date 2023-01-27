package com.example.carefully.domain.booking.entity;

import com.example.carefully.domain.booking.dto.BookingDto;
import com.example.carefully.domain.bookingRequest.entity.BookingRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity(name="Dwelling")
@DiscriminatorValue("Dwelling")
@NoArgsConstructor(access = PROTECTED)
public class Dwelling extends Booking {
    private TransactionMethod transactionMethod;
    private NumberOfRooms numberOfRooms;
    private String price;

    @Builder
    public Dwelling(Long id, TransactionMethod transactionMethod, NumberOfRooms numberOfRooms, String price) {
        super(id);
        this.transactionMethod = transactionMethod;
        this.numberOfRooms = numberOfRooms;
        this.price = price;
    }

    public static Dwelling dwellingRequest(BookingDto.DwellingReceiveRequest dwellingReceiveRequest) {
        return Dwelling.builder()
                .transactionMethod(TransactionMethod.valueOf(dwellingReceiveRequest.getTransactionMethodRequest().name()))
                .numberOfRooms(NumberOfRooms.valueOf(dwellingReceiveRequest.getNumberOfRoomsRequest().name()))
                .price(dwellingReceiveRequest.getPrice())
                .build();
    }
}

