package com.example.carefully.domain.post.dto;

import lombok.Getter;

@Getter
public enum PostResponseMessage {
    CREATE_POST_SUCCESS("글을 작성하는데 성공하였습니다.");

    private final String message;

    PostResponseMessage(String message){
        this.message = message;
    }
}
