package com.example.carefully.global.security.jwt.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.AuthorityException;

public class TokenEmptyException extends AuthorityException {
    public TokenEmptyException() {
        super(ErrorCode.TOKEN_EMPTY);
    }
}
