package com.example.carefully.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonPropertyOrder({"message", "result"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    public static <T> BaseResponse<T> create(T message){
        return new BaseResponse<>((String)message, null);
    }

    public static <T> BaseResponse<T> create(String message, T result){
        return new BaseResponse<>(message, result);
    }
}
