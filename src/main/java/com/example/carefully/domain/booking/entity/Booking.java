package com.example.carefully.domain.booking.entity;

import com.example.carefully.domain.booking.dto.BookingDto;
import com.example.carefully.domain.user.entity.BusinessType;
import com.example.carefully.domain.user.entity.Operation;
import com.example.carefully.domain.user.entity.General;
import com.example.carefully.global.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@DiscriminatorValue("Booking")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Booking extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate requestDate;

    @Column(nullable = false)
    private LocalTime requestTime;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private BusinessType businessType;

    @Column(nullable = false)
    @Enumerated(value = STRING)
    private BookingStatus bookingStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private General general;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operation_id")
    private Operation operation;

    @Builder
    public Booking(Long id, LocalDate requestDate, LocalTime requestTime, String content, BusinessType businessType, General general, Operation operation, BookingStatus bookingStatus) {
        this.id = id;
        this.general = general;
        this.requestDate = requestDate;
        this.requestTime = requestTime;
        this.operation = operation;
        this.content = content;
        this.businessType = businessType;
        this.bookingStatus = bookingStatus;
    }

    public static Booking request(General general, BookingDto.ReceiveRequest receiveRequest) {
        return Booking.builder()
                .general(general)
                .requestDate(receiveRequest.getRequestDate())
                .requestTime(receiveRequest.getRequestTime())
                .content(receiveRequest.getContent())
                .businessType(receiveRequest.getBusinessType())
                .bookingStatus(BookingStatus.valueOf("WAITING"))
                .build();
    }

//    //== 비지니스 메서드 ==//
//    public void accept() {
//        this.status = ACCEPT;
//    }
//
//    public void cancel() {
//        this.status = CANCEL;
//    }
}