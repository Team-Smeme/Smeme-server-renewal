package com.smeme.server.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
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

    @Value("${badge.welcome-badge-id}")
    private Long WELCOME_BADGE_ID;

    @Value("${jwt.KAKAO_URL}")
    private String KAKAO_URL;

    @Value("${fcm.file_path}")
    private String FIREBASE_CONFIG_PATH;

    @Value("${fcm.url}")
    private String FIREBASE_API_URI;

    @Value("${fcm.google_api}")
    private String GOOGLE_API_URI;

    @PostConstruct
    protected void init() {
        JWT_SECRET = Base64.getEncoder().encodeToString(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }
}
