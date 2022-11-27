package com.example.carefully.domain.comment.dto;

import lombok.Getter;

@Getter
public enum CommentResponseMessage {

    CREATE_COMMENT_SUCCESS("댓글을 작성하는데 성공하였습니다"),
    SEARCH_COMMENTS_SUCCESS("모든 댓글을 조회하는데 성공하였습니다");

    private final String message;

    CommentResponseMessage(final String message) {
        this.message = message;
    }
}
