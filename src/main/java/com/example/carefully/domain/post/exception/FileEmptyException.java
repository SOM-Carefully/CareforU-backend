package com.example.carefully.domain.post.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.EntityNotFoundException;

public class FileEmptyException extends EntityNotFoundException {
    public FileEmptyException() {
        super(ErrorCode.EMPTY_FILE);
    }
}
