package com.smeme.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SmemeServerRenewalApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmemeServerRenewalApplication.class, args);
    }

}
