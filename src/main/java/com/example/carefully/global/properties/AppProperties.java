package com.example.carefully.global.properties;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AppProperties {

    @Value("${coolsms.key}")
    public static String coolSmsKey;

    @Value("${coolsms.secret}")
    public static String coolSmsSecret;

    @Value("${coolsms.phone}")
    public static String coolSmsFromPhoneNumber;
}