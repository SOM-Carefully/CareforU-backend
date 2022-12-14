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
@Entity(name="Communication")
@DiscriminatorValue("Communication")
@NoArgsConstructor(access = PROTECTED)
public class Communication extends Booking {
    private String modelName;
    private boolean usim;

    @Builder
    public Communication(Long id, String content, User user, User admin, BookingStatus bookingStatus, BusinessType businessType, String userFileUrl, String adminFileUrl, String modelName, boolean usim) {
        super(id, content, user, admin, bookingStatus, businessType, userFileUrl, adminFileUrl);
        this.modelName = modelName;
        this.usim = usim;
    }

    public static Communication communicationRequest(User user, BookingDto.CommunicationReceiveRequest communicationReceiveRequest) {
        return Communication.builder()
                .user(user)
                .content(communicationReceiveRequest.getContent())
                .bookingStatus(BookingStatus.valueOf("WAITING"))
                .businessType(BusinessType.valueOf("COMMUNICATION"))
                .modelName(communicationReceiveRequest.getModelName())
                .usim(communicationReceiveRequest.isUsim())
                .build();
    }
}

