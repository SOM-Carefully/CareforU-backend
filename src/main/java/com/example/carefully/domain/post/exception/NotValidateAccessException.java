package com.example.carefully.domain.post.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.BusinessException;

public class NotValidateAccessException extends BusinessException {
    public NotValidateAccessException() {
        super(ErrorCode.ACCESS_NOT_VALIDATE);
    }
}
