package com.example.carefully.domain.comment.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.EntityNotFoundException;

public class CommentEmptyException extends EntityNotFoundException {
    public CommentEmptyException() {
        super(ErrorCode.COMMENT_NOT_FOUND);
    }
}
