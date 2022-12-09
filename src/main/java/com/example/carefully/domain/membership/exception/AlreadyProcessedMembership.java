package com.example.carefully.domain.membership.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.EntityNotFoundException;

public class AlreadyProcessedMembership extends EntityNotFoundException {
    public AlreadyProcessedMembership() { super(ErrorCode.MEMBERSHIP_ANOTHER_PROCESSED); }
}
