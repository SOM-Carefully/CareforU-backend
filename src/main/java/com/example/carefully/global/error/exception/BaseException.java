package com.example.carefully.global.error.exception;

import com.example.carefully.global.error.common.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class BaseException extends RuntimeException {
    private final ErrorCode errorCode;

    public BaseException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BaseException(ErrorCode errorCode, Throwable ex) {
        super(errorCode.getMessage(), ex);
        this.errorCode = errorCode;
    }
}
