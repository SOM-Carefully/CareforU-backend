package com.example.carefully.global.aop.pointcut;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* com.example.carefully.domain..*Controller*.*(..))")
    public void allController() {}
}
