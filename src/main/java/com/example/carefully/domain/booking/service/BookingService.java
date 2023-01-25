package com.example.carefully.domain.booking.service;

import com.example.carefully.domain.booking.dto.BookingDto;
import com.example.carefully.domain.booking.entity.BookingStatus;
import com.example.carefully.global.dto.SliceDto;

public interface BookingService {

    void educationRequest(BookingDto.EducationReceiveRequest educationReceiveRequest);
    void trafficRequest(BookingDto.TrafficReceiveRequest trafficReceiveRequest);
    void dwellingRequest(BookingDto.DwellingReceiveRequest dwellingReceiveRequest);
    void communicationRequest(BookingDto.CommunicationReceiveRequest communicationReceiveRequest);

    SliceDto<BookingDto.ServiceAllResponse> serviceAllLookup();

    BookingDto.EducationReceiveResponse educationLookup(Long bookingId);
    BookingDto.DwellingReceiveResponse dwellingLookup(Long bookingId);
    BookingDto.TrafficReceiveResponse trafficLookup(Long bookingId);
    BookingDto.CommunicationReceiveResponse communicationLookup(Long bookingId);

    SliceDto<BookingDto.ServiceAllResponse> userLookup();

    void accept(Long bookingId, BookingDto.ServiceAcceptRequest serviceAcceptRequest);
    void cancel(Long bookingId, BookingDto.ServiceRejectRequest serviceRejectRequest);
    void ongoing(Long bookingId);
    void complete(Long bookingId);
}
