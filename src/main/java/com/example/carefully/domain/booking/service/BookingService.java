package com.example.carefully.domain.booking.service;

import com.example.carefully.domain.booking.dto.BookingDto;

public interface BookingService {
    void request(BookingDto.ReceiveRequest receiveRequest);
}

