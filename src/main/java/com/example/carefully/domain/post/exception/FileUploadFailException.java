package com.example.carefully.domain.post.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.BusinessException;

public class FileUploadFailException extends BusinessException {
    public FileUploadFailException() {
        super(ErrorCode.FAIL_UPLOAD_FILE);
    }
}
