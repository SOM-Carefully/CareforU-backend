package com.example.carefully.global.error.dto;

import com.example.carefully.global.error.common.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final int status;
    private String errorMessage;

    private Map<String, String> errors;

    private ErrorResponse(final ErrorCode errorCode, Map<String, String> errors){
        this.status = errorCode.getStatus();
        this.errorMessage = errorCode.getMessage();
        this.errors = errors;
    }

    private ErrorResponse(final ErrorCode errorCode){
        this.status = errorCode.getStatus();
        this.errorMessage = errorCode.getMessage();
    }

    public static ErrorResponse create(final ErrorCode errorCode){
        return new ErrorResponse(errorCode);
    }

    public static ErrorResponse create(final ErrorCode code, Map<String, String> errors){
        return new ErrorResponse(code, errors);
    }
}
