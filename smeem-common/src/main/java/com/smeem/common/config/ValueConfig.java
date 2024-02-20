package com.smeem.common.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Getter
@Configuration
public class ValueConfig {

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Value("${fcm.smeem_title}")
    private String MESSAGE_TITLE;

    @Value("${fcm.smeem_body}")
    private String MESSAGE_BODY;

    @Value("${jwt.APPLE_URL}")
    private String APPLE_URL;

    @Value("${WELCOME_BADGE_ID}")
    private Long WELCOME_BADGE_ID;

    @Value("${jwt.KAKAO_URL}")
    private String KAKAO_URL;

    @Value("${fcm.file_path}")
    private String FIREBASE_CONFIG_PATH;

    @Value("${fcm.url}")
    private String FIREBASE_API_URI;

    @Value("${fcm.google_api}")
    private String GOOGLE_API_URI;

    @Value("${smeem.duration.expired}")
    private int DURATION_EXPIRED;

    @Value("${smeem.duration.remind}")
    private int DURATION_REMIND;

    public static final boolean DEFAULT_IS_PUBLIC_VALUE = true;

    @PostConstruct
    protected void init() {
        JWT_SECRET = Base64.getEncoder().encodeToString(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }
}
