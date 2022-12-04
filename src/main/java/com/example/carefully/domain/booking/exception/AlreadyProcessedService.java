package com.example.carefully.domain.booking.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.EntityNotFoundException;
import com.example.carefully.global.error.exception.InvalidValueException;

public class AlreadyProcessedService extends InvalidValueException {
    public AlreadyProcessedService() {
        super(ErrorCode.BOOKING_ALREADY_PROCESSED);
    }
}