package com.smeem.application.domain.auth;

import com.smeem.application.config.SmeemProperties;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class SecretKeyFactory {
    private final SmeemProperties smeemProperties;

    public SecretKey create() {
        val encodedKey = Base64.getEncoder().encodeToString(smeemProperties.getSecret().getKey().getBytes());
        return Keys.hmacShaKeyFor(encodedKey.getBytes());
    }
}
