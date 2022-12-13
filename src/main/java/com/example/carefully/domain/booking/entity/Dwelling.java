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

@Entity(name="Dwelling")
@DiscriminatorValue("Dwelling")
@NoArgsConstructor(access = PROTECTED)
public class Dwelling extends Booking {
    private TransactionMethod transactionMethod;
    private NumberOfRooms numberOfRooms;
    private String price;

    @Builder
    public Dwelling(Long id, String content, User user, User admin, BookingStatus bookingStatus, BusinessType businessType, String userFileUrl, String adminFileUrl, TransactionMethod transactionMethod, NumberOfRooms numberOfRooms, String price) {
        super(id, content, user, admin, bookingStatus, businessType, userFileUrl, adminFileUrl);
        this.transactionMethod = transactionMethod;
        this.numberOfRooms = numberOfRooms;
        this.price = price;
    }

    public static Dwelling dwellingRequest(User user, BookingDto.DwellingReceiveRequest dwellingReceiveRequest) {
        return Dwelling.builder()
                .user(user)
                .content(dwellingReceiveRequest.getContent())
                .bookingStatus(BookingStatus.valueOf("WAITING"))
                .businessType(BusinessType.valueOf("DWELLING"))
                .transactionMethod(TransactionMethod.valueOf(dwellingReceiveRequest.getTransactionMethodRequest().name()))
                .numberOfRooms(NumberOfRooms.valueOf(dwellingReceiveRequest.getNumberOfRoomsRequest().name()))
                .price(dwellingReceiveRequest.getPrice())
                .build();
    }
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