package com.example.carefully.domain.booking.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.EntityNotFoundException;

public class NotValidationBookingId  extends EntityNotFoundException {
    public NotValidationBookingId() {
        super(ErrorCode.BOOKING_NOT_FOUND);
    }
}
