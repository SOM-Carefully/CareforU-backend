package com.example.carefully.domain.booking.service;

import com.example.carefully.domain.booking.dto.BookingDto;
import com.example.carefully.domain.booking.entity.BookingStatus;
import com.example.carefully.global.dto.SliceDto;

public interface BookingService {

    void request(BookingDto.ReceiveRequest receiveRequest);

    BookingDto.ServiceResponse lookup(Long bookingId);

    SliceDto<BookingDto.ServiceResponse> userLookup();

    void update(Long bookingId, BookingDto.UpdateRequest updateRequest);

    void accept(Long bookingId);

    void cancel(Long bookingId);

    void complete(Long bookingId);
}

