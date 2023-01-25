package com.example.carefully.domain.membership.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.EntityNotFoundException;

public class NotValidationMembershipUser extends EntityNotFoundException {
    public NotValidationMembershipUser() { super(ErrorCode.MEMBERSHIP_NOT_VALIDATE_USER); }
}
