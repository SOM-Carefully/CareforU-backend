package com.example.carefully.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class RequestLogAspect {

    @Before("com.example.carefully.global.aop.pointcut.Pointcuts.allController()")
    public void doLog(JoinPoint joinPoint) {
        log.info("[trace] request={} args={}", joinPoint.getSignature(), joinPoint.getArgs());
    }
}
