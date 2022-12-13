package com.example.carefully.domain.booking.service;

import com.example.carefully.domain.booking.dto.BookingDto;
import com.example.carefully.domain.booking.entity.BookingStatus;
import com.example.carefully.global.dto.SliceDto;

public interface BookingService {

    void educationRequest(BookingDto.EducationReceiveRequest educationReceiveRequest);

    void trafficRequest(BookingDto.TrafficReceiveRequest trafficReceiveRequest);

    void dwellingRequest(BookingDto.DwellingReceiveRequest dwellingReceiveRequest);

    void communicationRequest(BookingDto.CommunicationReceiveRequest communicationReceiveRequest);

    SliceDto<BookingDto.ServiceResponse> serviceAllLookup();

    BookingDto.ServiceResponse lookup(Long bookingId);

    SliceDto<BookingDto.ServiceResponse> userLookup();

    void accept(Long bookingId);

    void cancel(Long bookingId);

    void complete(Long bookingId);
}

