package com.example.carefully.domain.booking.entity;

import com.example.carefully.domain.booking.dto.BookingDto;
import com.example.carefully.domain.user.entity.BusinessType;
import com.example.carefully.domain.user.entity.Operation;
import com.example.carefully.domain.user.entity.General;
import com.example.carefully.domain.user.repository.UserRepository;
import com.example.carefully.global.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

import static com.example.carefully.domain.booking.entity.BookingStatus.*;
import static com.example.carefully.global.utils.UserUtils.getCurrentUser;
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
    private LocalDateTime requestTime;

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
    public Booking(Long id, LocalDateTime requestTime, String content, BusinessType businessType, General general, Operation operation, BookingStatus bookingStatus) {
        this.id = id;
        this.general = general;
        this.requestTime = requestTime;
        this.operation = operation;
        this.content = content;
        this.businessType = businessType;
        this.bookingStatus = bookingStatus;
    }

    public static Booking request(General general, BookingDto.ReceiveRequest receiveRequest) {
        return Booking.builder()
                .general(general)
                .requestTime(receiveRequest.getRequestTime())
                .content(receiveRequest.getContent())
                .businessType(receiveRequest.getBusinessType())
                .bookingStatus(BookingStatus.valueOf("WAITING"))
                .build();
    }

    //== 비지니스 메서드 ==//

    public void update(LocalDateTime requestTime, BusinessType businessType, String content) {
        this.requestTime = requestTime;
        this.businessType = businessType;
        this.content = content;
    }

    public void operating(UserRepository userRepository) {
        Operation operation = (Operation) getCurrentUser(userRepository);
        this.operation = operation;
    }

    public void accept() {
        this.bookingStatus = ACCEPT;
    }

    public void cancel() {
        this.bookingStatus = CANCEL;
    }

    public void complete() {
        this.bookingStatus = COMPLETE;
    }

    public void setNullOperation() { this.operation = null; }
}