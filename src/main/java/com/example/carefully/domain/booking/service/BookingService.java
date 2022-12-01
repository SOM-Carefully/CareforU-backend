package com.example.carefully.domain.booking.service;

import com.example.carefully.domain.booking.dto.BookingDto;
import com.example.carefully.domain.booking.entity.Booking;

public interface BookingService {
    void request(BookingDto.ReceiveRequest receiveRequest);
    BookingDto.ServiceResponse lookup(Long bookingId);

}

