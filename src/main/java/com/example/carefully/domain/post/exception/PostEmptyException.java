package com.example.carefully.domain.post.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.EntityNotFoundException;

public class PostEmptyException extends EntityNotFoundException {
    public PostEmptyException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}
