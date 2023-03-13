package com.example.carefully.global.error.exception;

import com.example.carefully.global.error.common.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AuthorityException extends BaseException {

    public AuthorityException(ErrorCode errorCode){
        super(errorCode);
    }

    public AuthorityException(ErrorCode errorCode, Throwable ex) {
        super(errorCode, ex);
    }
}
