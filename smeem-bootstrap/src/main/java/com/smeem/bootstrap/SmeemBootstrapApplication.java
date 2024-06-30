package com.smeem.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.smeem"})
public class SmeemBootstrapApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmeemBootstrapApplication.class, args);
    }
}
