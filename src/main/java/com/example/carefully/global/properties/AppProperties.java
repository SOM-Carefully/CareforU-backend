package com.example.carefully.global.properties;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AppProperties {
    // 바꾸실 때는 설정 파일로 따로 분리하셔서 작업해주세요!
    public final String coolSmsKey = "NCSQF38UHJMLWRBN";
    public final String coolSmsSecret = "XWHVUXVBYEMUJZNBYBNUCQ6DR6OHW3XP";
    public final String coolSmsFromPhoneNumber = "010-4537-4551";
}