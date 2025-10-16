package com.fintech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FintechLoginModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(FintechLoginModuleApplication.class, args);
    }
}
