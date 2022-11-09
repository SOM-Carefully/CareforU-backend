package com.example.carefully.domain.post.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.BusinessException;

public class FileWrongExtensionException extends BusinessException {
    public FileWrongExtensionException() {
        super(ErrorCode.FILE_WRONG_EXTENSION);
    }
}
