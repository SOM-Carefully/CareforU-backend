package com.example.carefully.global.properties;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AppProperties {

    @Value("${coolsms.key}")
    private String coolSmsKey;

    @Value("${coolsms.secret}")
    private String coolSmsSecret;

    @Value("${coolsms.phone}")
    private String coolSmsFromPhoneNumber;
}