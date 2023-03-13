package com.example.carefully.global.security.jwt.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.AuthorityException;

public class TokenValidFailException extends AuthorityException {
    public TokenValidFailException(Throwable ex) {
        super(ErrorCode.INVALID_JWT, ex);
    }
}
