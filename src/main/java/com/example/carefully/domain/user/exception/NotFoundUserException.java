package com.example.carefully.domain.user.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.UserStatusException;

public class NotFoundUserException extends UserStatusException {
    public NotFoundUserException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
