package com.example.carefully.domain.post.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum PostRole {
    NOTICE, FREE;

    public static boolean isFree(String postRole) {
       return PostRole.FREE.equals(PostRole.valueOf(postRole));
    }
}
