package com.example.carefully.domain.user.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.UserStatusException;

public class AuthenticationNumberMismatchException extends UserStatusException {
    public AuthenticationNumberMismatchException() {
        super(ErrorCode.INVALID_NUMBER);
    }
}