package com.example.carefully.domain.user.service.impl;

import com.example.carefully.domain.user.dao.SmsCertificationDao;
import com.example.carefully.domain.user.dto.UserDto;
import com.example.carefully.domain.user.exception.AuthenticationNumberMismatchException;
import com.example.carefully.domain.user.exception.NotSendNumberException;
import com.example.carefully.domain.user.service.SmsService;
import com.example.carefully.global.common.coolSms.SmsMessageTemplate;
import lombok.RequiredArgsConstructor;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static com.example.carefully.global.common.coolSms.coolSmsConstants.APP_VERSION;
import static com.example.carefully.global.common.coolSms.coolSmsConstants.SMS_TYPE;
import static com.example.carefully.global.utils.certification.RandomNumberGeneration.makeRandomNumber;

@RequiredArgsConstructor
@Service
public class SmsCertificationServiceImpl implements SmsService {

    private final SmsCertificationDao smsCertificationDao;

    // private final AppProperties appProperties;
    @Value("${coolsms.key}")
    private String coolSmsKey;
    @Value("${coolsms.secret}")
    private String coolSmsSecret;
    @Value("${coolsms.phone}")
    private String coolSmsFromPhoneNumber;

    // 인증 메세지 내용 생성
    public String makeSmsContent(String certificationNumber) {
        SmsMessageTemplate content = new SmsMessageTemplate();
        return content.builderCertificationContent(certificationNumber);
    }

    public HashMap<String, String> makeParams(String to, String text) {
        HashMap<String, String> params = new HashMap<>();
        params.put("from", coolSmsFromPhoneNumber);
        params.put("type", SMS_TYPE);
        params.put("app_version", APP_VERSION);
        params.put("to", to);
        params.put("text", text);
        return params;
    }

    // coolSms API를 이용하여 인증번호 발송하고, 발송 정보를 Redis에 저장
    public void sendSms(String phone) {
        DefaultMessageService messageService = NurigoApp.INSTANCE.initialize(coolSmsKey, coolSmsSecret, "https://api.coolsms.co.kr");

        Message coolsms = new Message();
        String randomNumber = makeRandomNumber();
        String content = makeSmsContent(randomNumber);
        HashMap<String, String> params = makeParams(phone, content);

        coolsms.setFrom(coolSmsFromPhoneNumber);
        coolsms.setTo(phone);
        coolsms.setText(content);

        try {
            messageService.sendOne(new SingleMessageSendingRequest((coolsms)));
        } catch (Exception exception) {
            throw new NotSendNumberException();
        }

        smsCertificationDao.createSmsCertification(phone, randomNumber);
    }

    public void verifySms(UserDto.SmsCertificationRequest requestDto) {
        if (isVerify(requestDto)) {
            throw new AuthenticationNumberMismatchException();
        }
        smsCertificationDao.removeSmsCertification(requestDto.getPhone());
    }

    private boolean isVerify(UserDto.SmsCertificationRequest requestDto) {
        return !(smsCertificationDao.hasKey(requestDto.getPhone()) &&
                smsCertificationDao.getSmsCertification(requestDto.getPhone())
                        .equals(requestDto.getCertificationNumber()));
    }
}