package com.example.carefully.domain.booking.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.EntityNotFoundException;

public class NotValidationServiceAdmin extends EntityNotFoundException {
    public NotValidationServiceAdmin() {
        super(ErrorCode.BOOKING_ANOTHER_PROCESSED);
    }
}
