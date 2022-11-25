package com.example.carefully.domain.user.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.UserStatusException;

public class NotValidationPasswordException extends UserStatusException {
    public NotValidationPasswordException() {
        super(ErrorCode.PASSWORD_NOT_CORRECT);
    }
}