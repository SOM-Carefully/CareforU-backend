package com.example.carefully.domain.post.dto;

import lombok.Getter;

@Getter
public enum PostResponseMessage {
    CREATE_POST_SUCCESS("글을 작성하는데 성공하였습니다."),
    UPDATE_POST_SUCCESS("글을 수정하는데 성공하였습니다."),
    GET_POST_DETAIL_SUCCESS("글 상세정보를 가져오는데 성공하였습니다."),
    GET_POST_LIST_SUCCESS("글 리스트를 가져오는데 성공하였습니다."),
    DELETE_POST_SUCCESS("글을 삭제하는데 성공하였습니다."),
    FILE_UPLOAD_SUCCESS("파일 업로드에 성공하였습니다.");

    private final String message;

    PostResponseMessage(final String message){
        this.message = message;
    }
}
