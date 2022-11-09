package com.example.carefully.domain.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        return LocalDateTime.now() + "deploy success";
    }
}

