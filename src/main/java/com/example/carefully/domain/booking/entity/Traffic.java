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

@Getter
@Entity(name="Traffic")
@DiscriminatorValue("Traffic")
@NoArgsConstructor(access = PROTECTED)
public class Traffic extends Booking {
    private CarType carType;
    private String price;

    @Builder
    public Traffic(Long id, String userContent, String adminContent, User user, User admin, BookingStatus bookingStatus, BusinessType businessType, String userFileUrl, String adminFileUrl, CarType carType, String price) {
        super(id, userContent, adminContent, user, admin, bookingStatus, businessType, userFileUrl, adminFileUrl);
        this.carType = carType;
        this.price = price;
    }

    public static Traffic trafficRequest(User user, BookingDto.TrafficReceiveRequest trafficReceiveRequest) {
        return Traffic.builder()
                .user(user)
                .userContent(trafficReceiveRequest.getContent())
                .bookingStatus(BookingStatus.valueOf("WAITING"))
                .businessType(BusinessType.valueOf("TRAFFIC"))
                .carType(CarType.valueOf(trafficReceiveRequest.getCarTypeRequest().name()))
                .price(trafficReceiveRequest.getPrice())
                .build();
    }
}

