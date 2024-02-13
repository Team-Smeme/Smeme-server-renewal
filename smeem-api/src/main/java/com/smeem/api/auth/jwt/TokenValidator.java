package com.smeem.api.auth.jwt;

import com.smeem.common.exception.AuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.smeem.api.auth.jwt.JwtValidationType.INVALID_JWT_TOKEN;
import static com.smeem.api.auth.jwt.JwtValidationType.VALID_JWT;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenValidator {
    private final SecretKeyFactory secretKeyFactory;

    public JwtValidationType validateToken(String token) {
        try {
            getBody(token);
            return VALID_JWT;
        } catch (AuthException ex) {
            log.error(String.valueOf(INVALID_JWT_TOKEN));
            return INVALID_JWT_TOKEN;
        }
    }

    public Long getUserFromJwt(String token) {
        Claims claims = getBody(token);
        return Long.parseLong(claims.get("memberId").toString());
    }

    private Claims getBody(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKeyFactory.create())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
