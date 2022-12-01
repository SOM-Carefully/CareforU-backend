package com.example.carefully.domain.booking.service;

import com.example.carefully.domain.booking.dto.BookingDto;
import com.example.carefully.domain.booking.entity.Booking;
import com.example.carefully.domain.user.entity.BusinessType;
import com.example.carefully.domain.user.entity.User;

import java.util.List;

public interface BookingService {
    void request(BookingDto.ReceiveRequest receiveRequest);
    BookingDto.ServiceResponse lookup(Long bookingId);

    List<BookingDto.ServiceResponse> userLookup();

    List<BookingDto.ServiceResponse> operationLookup();

    List<BookingDto.ServiceResponse> filterByBusinessType(BusinessType businessType);

}

