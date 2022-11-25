package com.example.carefully.domain.user.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    MAN("남자"), WOMAN("여자"), NA("해당 없음");

    private final String description;
}