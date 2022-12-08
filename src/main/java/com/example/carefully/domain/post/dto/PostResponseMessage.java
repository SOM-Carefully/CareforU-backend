package com.example.carefully.domain.post.dto;

import lombok.Getter;

@Getter
public enum PostResponseMessage {
    CREATE_POST_SUCCESS("글을 작성하는데 성공하였습니다."),
    UPDATE_POST_SUCCESS("글을 수정하는데 성공하였습니다."),
    GET_POST_DETAIL_SUCCESS("글 상세정보를 가져오는데 성공하였습니다."),
    GET_POST_LIST_SUCCESS("글 리스트를 가져오는데 성공하였습니다."),
    DELETE_POST_SUCCESS("글을 삭제하는데 성공하였습니다."),
    FILE_UPLOAD_SUCCESS("파일 업로드에 성공하였습니다."),
    UPLOAD_QUESTION_SUCCESS("문의 사항 등록에 성공하였습니다."),
    UPDATE_QUESTION_SUCCESS("문의 사항 수정에 성공하였습니다."),
    GET_QUESTION_DETAIL_SUCCESS("문의 사항 상세정보를 가져오는데 성공하였습니다."),
    GET_QUESTION_LIST_SUCCESS("문의 사항 리스트를 가져오는데 성공하였습니다."),
    DELETE_QUESTION_SUCCESS("문의 사항을 삭제하는데 성공하였습니다."),
    CREATE_CATEGORY_SUCCESS("카테고리를 생성하는데 성공하였습니다."),
    GET_CATEGORY_LIST_SUCCESS("카테고리 리스트를 가져오는데 성공하였습니다."),
    UPDATE_CATEGORY_SUCCESS("카테고리 이름을 수정하는데 성공하였습니다."),
    DELETE_CATEGORY_SUCCESS("카테고리를 삭제하는데 성공하였습니다.");

    private final String message;

    PostResponseMessage(final String message){
        this.message = message;
    }
}
