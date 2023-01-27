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
@Entity(name="Traffic")
@DiscriminatorValue("Traffic")
@NoArgsConstructor(access = PROTECTED)
public class Traffic extends Booking {
    private CarType carType;
    private String price;

    @Builder
    public Traffic(Long id, CarType carType, String price) {
        super(id);
        this.carType = carType;
        this.price = price;
    }

    public static Traffic trafficRequest(BookingDto.TrafficReceiveRequest trafficReceiveRequest) {
        return Traffic.builder()
                .carType(CarType.valueOf(trafficReceiveRequest.getCarTypeRequest().name()))
                .price(trafficReceiveRequest.getPrice())
                .build();
    }
}

