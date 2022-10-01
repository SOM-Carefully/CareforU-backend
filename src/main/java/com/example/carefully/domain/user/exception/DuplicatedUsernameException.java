package com.example.carefully.domain.user.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.UserStatusException;

public class DuplicatedUsernameException extends UserStatusException {
    public DuplicatedUsernameException() {
        super(ErrorCode.USER_EXIST);
    }
}
