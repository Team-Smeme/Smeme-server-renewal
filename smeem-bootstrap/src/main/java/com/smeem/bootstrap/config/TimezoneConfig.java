package com.smeem.bootstrap.config;

import java.util.TimeZone;


import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimezoneConfig {

    private static final String KST_TIMEZONE = "Asia/Seoul";

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone(KST_TIMEZONE));
    }
}
