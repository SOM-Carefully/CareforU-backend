package com.example.carefully.domain.user.service;

import com.example.carefully.domain.user.dto.UserDto;

public interface SmsService {
    void sendSms(String phone);
    void verifySms(UserDto.SmsCertificationRequest requestDto);
}
