package com.example.carefully.global.error.exception;

import com.example.carefully.global.error.common.ErrorCode;

public class UserExistException extends BaseException{
    public UserExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
