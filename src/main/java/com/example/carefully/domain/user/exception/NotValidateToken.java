package com.example.carefully.domain.user.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.UserStatusException;

public class NotValidateToken extends UserStatusException {
    public NotValidateToken() {
        super(ErrorCode.INVALID_JWT);
    }
}