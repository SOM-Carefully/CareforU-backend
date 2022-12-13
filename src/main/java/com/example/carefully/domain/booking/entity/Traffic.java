package com.example.carefully.domain.booking.entity;

import com.example.carefully.domain.booking.dto.BookingDto;
import com.example.carefully.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static lombok.AccessLevel.PROTECTED;

@Entity(name="Traffic")
@DiscriminatorValue("Traffic")
@NoArgsConstructor(access = PROTECTED)
public class Traffic extends Booking {
    private CarType carType;
    private String price;

    @Builder
    public Traffic(Long id, String content, User user, User admin, BookingStatus bookingStatus, BusinessType businessType, String userFileUrl, String adminFileUrl, CarType carType, String price) {
        super(id, content, user, admin, bookingStatus, businessType, userFileUrl, adminFileUrl);
        this.carType = carType;
        this.price = price;
    }

    public static Traffic trafficRequest(User user, BookingDto.TrafficReceiveRequest trafficReceiveRequest) {
        return Traffic.builder()
                .user(user)
                .content(trafficReceiveRequest.getContent())
                .bookingStatus(BookingStatus.valueOf("WAITING"))
                .businessType(BusinessType.valueOf("TRAFFIC"))
                .carType(CarType.valueOf(trafficReceiveRequest.getCarTypeRequest().name()))
                .price(trafficReceiveRequest.getPrice())
                .build();
    }
}

@Getter
@RequiredArgsConstructor
enum CarType {
    COMPACT("소형"), MIDSIZE("중형"), SUV("SUV");

    private final String description;
}