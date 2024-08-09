package com.smeem.application.domain.auth;

import com.smeem.common.util.SmeemProperty;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import static java.util.Base64.getEncoder;

@Component
@RequiredArgsConstructor
public class SecretKeyFactory {
    private final SmeemProperty smeemProperty;

    public SecretKey create() {
        val encodedKey = getEncoder().encodeToString(smeemProperty.getSMEEM_SECRET_KEY().getBytes());
        return Keys.hmacShaKeyFor(encodedKey.getBytes());
    }
}
