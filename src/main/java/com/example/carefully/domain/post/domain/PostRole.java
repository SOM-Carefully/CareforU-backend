package com.example.carefully.domain.post.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;


@Getter
@RequiredArgsConstructor
public enum PostRole {
    NOTICE, QUEST, FREE;

    public static boolean isFree(String postRole) {
       return PostRole.FREE.equals(PostRole.valueOf(postRole));
    }
}
