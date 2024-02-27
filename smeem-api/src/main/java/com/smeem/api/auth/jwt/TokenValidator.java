package com.smeem.api.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.smeem.api.auth.jwt.JwtValidationType.INVALID_JWT;
import static com.smeem.api.auth.jwt.JwtValidationType.VALID_JWT;

@Component
@RequiredArgsConstructor
public class TokenValidator {
    private final SecretKeyFactory secretKeyFactory;

    public JwtValidationType validateToken(String token) {
        try {
            getBody(token);
            return VALID_JWT;
        } catch (RuntimeException ex) {
            return INVALID_JWT;
        }
    }

    public long getUserFromJwt(String token) {
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
