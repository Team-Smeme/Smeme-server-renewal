package com.smeem.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.smeem.common", "com.smeem.api", "com.smeem.domain", "com.smeem.external", "com.smeem.batch"})
@EnableJpaRepositories(basePackages = {"com.smeem.domain"})
@EntityScan(basePackages = {"com.smeem.domain"})
public class SmemeServerRenewalApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmemeServerRenewalApplication.class, args);
    }

}
