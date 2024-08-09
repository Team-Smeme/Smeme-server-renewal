package com.smeem.common.util;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@Getter
public class SmeemProperty {
    /**
     * http
     */
    @Value("${smeem.secret.key}")
    private String SMEEM_SECRET_KEY;

    /**
     * oauth-module
     */
    @Value("${oauth.url.apple}")
    private String APPLE_URL;

    @Value("${oauth.url.kakao}")
    private String KAKAO_URL;

    @PostConstruct
    protected void init() {
        SMEEM_SECRET_KEY = Base64.getEncoder().encodeToString(SMEEM_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }
}
