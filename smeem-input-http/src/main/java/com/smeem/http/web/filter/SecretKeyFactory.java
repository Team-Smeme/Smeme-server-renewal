package com.smeem.http.web.filter;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static java.util.Base64.getEncoder;

@Component
public class SecretKeyFactory {
    @Value("${smeem.secret.key}")
    private String SMEEM_SECRET_KEY;

    public SecretKey create() {
        val encodedKey = getEncoder().encodeToString(SMEEM_SECRET_KEY.getBytes());
        return Keys.hmacShaKeyFor(encodedKey.getBytes());
    }

    @PostConstruct
    protected void init() {
        SMEEM_SECRET_KEY = Base64.getEncoder().encodeToString(SMEEM_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }
}
