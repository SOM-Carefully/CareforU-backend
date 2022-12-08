package com.example.carefully.domain.category.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.EntityNotFoundException;

public class CategoryEmptyException extends EntityNotFoundException {
    public CategoryEmptyException() {
        super(ErrorCode.CATEGORY_NOT_FOUND);
    }
}
