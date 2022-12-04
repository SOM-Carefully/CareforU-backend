package com.example.carefully.domain.booking.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.EntityNotFoundException;

public class NotValidationServiceOperation extends EntityNotFoundException {
    public NotValidationServiceOperation() {
        super(ErrorCode.BOOKING_ANOTHER_PROCESSED);
    }
}
