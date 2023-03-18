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

    /**
     * SMS 인증 메세지 내용을 생성한다.
     *
     * @param certificationNumber 인증번호
     * @return smsMessageTemplate 입력된 인증번호가 적용된 본인인증 문자 템플릿
     */
    public String makeSmsContent(String certificationNumber) {
        SmsMessageTemplate content = new SmsMessageTemplate();
        return content.builderCertificationContent(certificationNumber);
    }

    /**
     * coolSMS API를 사용하기 위한 parameter 등록.
     *
     * @param to 휴대폰번호, text SMS 인증 메세지 내용
     * @return params
     */
    public HashMap<String, String> makeParams(String to, String text) {
        HashMap<String, String> params = new HashMap<>();
        params.put("from", coolSmsFromPhoneNumber);
        params.put("type", SMS_TYPE);
        params.put("app_version", APP_VERSION);
        params.put("to", to);
        params.put("text", text);
        return params;
    }

    /**
     * coolSms API를 이용하여 인증번호 발송하고, 발송 정보를 Redis에 저장한다. - 발송 실패시 error
     *
     * @param phone 휴대폰번호
     */
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

    /**
     * redis에 등록된 휴대폰 번호로 전송된 인증 번호가 입력된 정보와 일치하는지 검증하고 일치할 경우 redis에서 정보를 삭제한다.
     *
     * @param requestDto 휴대폰번호, 인증 번호
     */
    public void verifySms(UserDto.SmsCertificationRequest requestDto) {
        if (isVerify(requestDto)) {
            throw new AuthenticationNumberMismatchException();
        }
        smsCertificationDao.removeSmsCertification(requestDto.getPhone());
    }

    /**
     * redis에 등록된 휴대폰 번호로 전송된 인증 번호가 입력된 정보와 일치하는지 검증한다. - 일치하지 않을 경우 error
     *
     * @param requestDto 휴대폰번호, 인증 번호
     * @return boolean
     */
    private boolean isVerify(UserDto.SmsCertificationRequest requestDto) {
        return !(smsCertificationDao.hasKey(requestDto.getPhone()) &&
                smsCertificationDao.getSmsCertification(requestDto.getPhone())
                        .equals(requestDto.getCertificationNumber()));
    }
}