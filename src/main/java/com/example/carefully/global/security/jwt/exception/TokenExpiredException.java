package com.example.carefully.global.security.jwt.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.AuthorityException;

public class TokenExpiredException extends AuthorityException {
    public TokenExpiredException(Throwable ex) {
        super(ErrorCode.TOKEN_EXPIRED, ex);
    }
}
