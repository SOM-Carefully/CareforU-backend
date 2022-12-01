package com.example.carefully.domain.booking.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.EntityNotFoundException;

public class NotValidationUser extends EntityNotFoundException {
    public NotValidationUser() {
        super(ErrorCode.INVALID_USER_JWT);
    }
}
