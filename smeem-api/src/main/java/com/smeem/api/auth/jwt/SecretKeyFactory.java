package com.smeem.api.auth.jwt;

import com.smeem.common.config.ValueConfig;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import static java.util.Base64.getEncoder;

@Component
@RequiredArgsConstructor
public class SecretKeyFactory {
    private final ValueConfig valueConfig;
    SecretKey create() {
        String encodedKey = getEncoder().encodeToString(valueConfig.getJWT_SECRET().getBytes());
        return Keys.hmacShaKeyFor(encodedKey.getBytes());
    }

}
