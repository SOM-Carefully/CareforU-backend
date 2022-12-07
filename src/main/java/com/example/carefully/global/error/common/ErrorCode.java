package com.example.carefully.global.error.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // USER
    REQUEST_ERROR(2000, "입력값을 확인해주세요."),
    EMPTY_JWT(2001, "JWT를 입력해주세요."),
    INVALID_JWT(2002, "유효하지 않은 JWT입니다."),
    INVALID_REFRESH_JWT(2003, "유효하지 않은 리프레시 토큰입니다."),
    INVALID_USER_JWT(2003,"권한이 없는 유저의 접근입니다."),
    FAIL_UPLOAD_FILE( 2004, "파일 업로드에 실패하셨습니다."),
    EMPTY_FILE(2005, "파일을 확인해 주세요."),
    FILE_SIZE_EXCEED(2006, "파일 사이즈를 확인해 주세요."),
    FILE_WRONG_EXTENSION(2007, "파일 확장자가 잘못되었습니다."),
    INTERNAL_SERVER_ERROR(5000, "서버 오류입니다. "),
    METHOD_NOT_ALLOWED(4000, "요청 방식이 잘못되었습니다."),
    INVALID_SECRET_KEY(2007, "유효하지 않은 시크릿 키입니다."),
    USER_EXIST(400, "이미 존재하는 유저입니다."),
    USER_NOT_FOUND(400, "유저를 찾을 수 없습니다."),
    ROLE_NOT_VALIDATE(400, "유효하지 않은 역할입니다."),
    PASSWORD_NOT_CORRECT(400, "비밀번호가 일치하지 않습니다."),

    //TODO 도메인 별로 분류해서 추가해주세요.

    //POST
    POST_NOT_FOUND(3010, "존재하지 않는 글입니다."),

    //COMMENT
    COMMENT_NOT_FOUND(4010, "존재하지 않는 댓글입니다."),

    //BOOKING
    BOOKING_NOT_FOUND(5010, "서비스 신청이 존재하지 않습니다."),
    BOOKING_ALREADY_PROCESSED(2000, "이미 처리된 서비스입니다."),
    BOOKING_ANOTHER_PROCESSED(2000, "다른 운영팀 회원이 이미 처리한 서비스입니다.")
    ;

    private final String message;
    private final int status;

    ErrorCode(final int status, final String message){
        this.status = status;
        this.message = message;
    }
}
