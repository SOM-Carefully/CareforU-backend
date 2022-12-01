package com.example.carefully.domain.booking.service;

import com.example.carefully.domain.booking.dto.BookingDto;
import com.example.carefully.global.dto.SliceDto;

public interface BookingService {
    void request(BookingDto.ReceiveRequest receiveRequest);
    BookingDto.ServiceResponse lookup(Long bookingId);

    SliceDto<BookingDto.ServiceResponse> userLookup();

    SliceDto<BookingDto.ServiceResponse> operationLookup();
}

