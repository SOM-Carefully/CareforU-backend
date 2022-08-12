package com.example.carefully;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CarefullyApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarefullyApplication.class, args);
    }

}
