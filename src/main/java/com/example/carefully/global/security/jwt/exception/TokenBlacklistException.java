package com.example.carefully.global.security.jwt.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.AuthorityException;

public class TokenBlacklistException extends AuthorityException {
    public TokenBlacklistException() {
        super(ErrorCode.TOKEN_BLACKLIST);
    }
}
