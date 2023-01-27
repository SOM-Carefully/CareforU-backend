package com.example.carefully.global.properties;

import lombok.Getter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Getter
@RequiredArgsConstructor
public class AppProperties {

    @Value("${coolsms.key}")
    private final String coolSmsKey;

    @Value("${coolsms.secret}")
    private final String coolSmsSecret;

    @Value("${coolsms.phone}")
    private final String coolSmsFromPhoneNumber;
}