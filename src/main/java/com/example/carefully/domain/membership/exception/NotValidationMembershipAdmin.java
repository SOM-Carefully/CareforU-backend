package com.example.carefully.domain.membership.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.EntityNotFoundException;

public class NotValidationMembershipAdmin extends EntityNotFoundException {
    public NotValidationMembershipAdmin() { super(ErrorCode.MEMBERSHIP_ANOTHER_PROCESSED); }
}
