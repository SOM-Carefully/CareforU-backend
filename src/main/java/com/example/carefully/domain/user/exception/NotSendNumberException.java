package com.example.carefully.domain.user.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.UserStatusException;

public class NotSendNumberException extends UserStatusException {
    public NotSendNumberException() {
        super(ErrorCode.NOT_SEND_NUMBER);
    }
}
