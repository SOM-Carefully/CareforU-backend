package com.example.carefully.global.error.handler;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.dto.ErrorResponse;
import com.example.carefully.global.error.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.stream.Collectors;

@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class RestControllerExceptionHandler {

    /**
     * @Valid 관련 예외
     */
    @SuppressWarnings("ConstantConditions")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validationExceptionHandle(MethodArgumentNotValidException ex) {
        log.error("handleMethodArgumentNotValidException", ex);
        return ResponseEntity.badRequest().body(ErrorResponse.create(ErrorCode.REQUEST_ERROR,
                ex.getBindingResult().getFieldErrors().stream()
                        .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage))));
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.error("handleHttpRequestMethodNotSupportedException", ex);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(ErrorResponse.create(ErrorCode.METHOD_NOT_ALLOWED));
    }

    /**
     * Authentication 객체가 필요한 권한을 보유하지 않은 예외
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("handleAccessDeniedException", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.create(ErrorCode.INVALID_USER_JWT));
    }

    /**
     * 파일 업로드 용량 초과시 발생
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    protected ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.info("handleMaxUploadSizeExceededException", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.create(ErrorCode.FILE_SIZE_EXCEED));
    }

    /**
     * 커스텀 : 비즈니스 예외
     */
    @ExceptionHandler({BusinessException.class, InvalidValueException.class, EntityNotFoundException.class})
    protected ResponseEntity<ErrorResponse> handleBusinessException(BaseException ex) {
        log.error("handleBusinessException", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.create(ex.getErrorCode()));
    }

    /**
     * 커스텀 : 유저와 인증 관련한 모든 예외
     */
    @ExceptionHandler({AuthorityException.class, UserStatusException.class})
    protected ResponseEntity<ErrorResponse> handleAuthorityException(BaseException ex) {
        log.error("handleAuthorityException", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.create(ex.getErrorCode()));
    }

    /**
     *  커스텀 : 핸들링 하지 않는 나머지 모든 예외(체크 예외)
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<String> handleException(Exception ex) {
        log.error("handleException", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
