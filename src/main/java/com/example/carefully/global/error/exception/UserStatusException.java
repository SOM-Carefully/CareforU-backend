package com.example.carefully.global.error.exception;

import com.example.carefully.global.error.common.ErrorCode;

public class UserStatusException extends BaseException{
    public UserStatusException(ErrorCode errorCode) {
        super(errorCode);
    }
}
