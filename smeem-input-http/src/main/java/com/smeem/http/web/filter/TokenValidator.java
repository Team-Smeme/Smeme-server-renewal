package com.smeem.http.web.filter;

import com.smeem.application.domain.auth.SecretKeyFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenValidator {
    private final SecretKeyFactory secretKeyFactory;

    public JwtValidationType validateToken(String token) {
        try {
            getBody(token);
            return JwtValidationType.VALID_JWT;
        } catch (RuntimeException ex) {
            return JwtValidationType.INVALID_JWT;
        }
    }

    public long getUserFromJwt(String token) {
        val claims = getBody(token);
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
