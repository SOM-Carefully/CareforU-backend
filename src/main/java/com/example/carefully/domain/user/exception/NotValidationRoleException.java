package com.example.carefully.domain.user.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.UserStatusException;

public class NotValidationRoleException extends UserStatusException {
    public NotValidationRoleException() {
        super(ErrorCode.ROLE_NOT_VALIDATE);
    }
}
