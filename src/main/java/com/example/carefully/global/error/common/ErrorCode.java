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
    USER_EXIST(4001, "이미 존재하는 유저입니다."),
    USER_NOT_FOUND(4002, "유저를 찾을 수 없습니다."),
    ROLE_NOT_VALIDATE(4003, "유효하지 않은 역할입니다."),
    PASSWORD_NOT_CORRECT(4004, "비밀번호가 일치하지 않습니다."),
    INVALID_NUMBER(4005, "인증번호가 일치하지 않습니다."),
    NOT_SEND_NUMBER(4006, "인증번호가 정상적으로 전송되지 않았습니다."),

    //TODO 도메인 별로 분류해서 추가해주세요.

    //POST
    POST_NOT_FOUND(3010, "존재하지 않는 글입니다."),
    CATEGORY_NOT_FOUND(3011, "존재하지 않는 카테고리입니다."),
    QUEST_NOT_FOUND(3012, "존재하지 않는 문의글입니다."),
    ACCESS_NOT_VALIDATE(3013, "현재 회원의 등급보다 더 높은 등급의 게시판에는 접근할 수 없습니다."),
    WRITE_NOT_VALIDATE(3014, "현재 회원의 등급과 같은 등급의 게시판에만 글을 작성할 수 있습니다."),

    //COMMENT
    COMMENT_NOT_FOUND(4010, "존재하지 않는 댓글입니다."),

    //BOOKING
    BOOKING_NOT_FOUND(5010, "서비스 신청이 존재하지 않습니다."),
    BOOKING_ALREADY_PROCESSED(4100, "이미 처리된 서비스입니다."),
    BOOKING_ANOTHER_PROCESSED(4110, "다른 운영팀 회원이 이미 처리한 서비스입니다."),

    //MEMBERSHIP
    MEMBERSHIP_ALREADY_PROCESSED(4210, "이미 처리된 회원가입 신청입니다."),
    MEMBERSHIP_ANOTHER_PROCESSED(4220, "다른 운영팀 회원이 이미 처리한 회원가입 신청입니다."),
    MEMBERSHIP_NOT_VALIDATE_USER(4230, "어드민 회원이 아니거나 회원가입을 신청한 유저가 아닙니다.");

    private final String message;
    private final int status;

    ErrorCode(final int status, final String message){
        this.status = status;
        this.message = message;
    }
}
