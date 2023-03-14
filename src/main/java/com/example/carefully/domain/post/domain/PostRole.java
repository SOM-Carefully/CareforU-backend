package com.example.carefully.domain.post.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 게시판 타입
 */
@Getter
@RequiredArgsConstructor
public enum PostRole {

    /**
     * 공지 게시판
     */
    NOTICE,

    /**
     * 자유 게시판
     */
    FREE
}
