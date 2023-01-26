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
@Entity(name="Communication")
@DiscriminatorValue("Communication")
@NoArgsConstructor(access = PROTECTED)
public class Communication extends Booking {
    private String modelName;
    private boolean usim;

    @Builder
    public Communication(Long id, String modelName, boolean usim) {
        super(id);
        this.modelName = modelName;
        this.usim = usim;
    }

    public static Communication communicationRequest(BookingDto.CommunicationReceiveRequest communicationReceiveRequest) {
        return Communication.builder()
                .modelName(communicationReceiveRequest.getModelName())
                .usim(communicationReceiveRequest.isUsim())
                .build();
    }
}

