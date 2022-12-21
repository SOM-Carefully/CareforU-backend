package com.example.carefully.domain.post.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.BusinessException;

public class NotValidateWriteException extends BusinessException {
    public NotValidateWriteException() {
        super(ErrorCode.WRITE_NOT_VALIDATE);
    }
}
