package com.smeem.application.domain.auth;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenGenerator {
    private final SecretKeyFactory secretKeyFactory;

    private static final Long ACCESS_TOKEN_EXPIRATION_TIME = 60 * 60 * 1000 * 2 * 12 * 1000000L;
    private static final Long REFRESH_TOKEN_EXPIRATION_TIME = 60 * 60 * 1000 * 24 * 14L;

    public String generateAccessToken(long memberId) {
        val authentication = UserAuthentication.create(memberId);
        return generateToken(authentication, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String generateRefreshToken(long memberId) {
        val authentication = UserAuthentication.create(memberId);
        return generateToken(authentication, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    private String generateToken(Authentication authentication, Long tokenExpirationTime) {
        val now = new Date();

        val claims = Jwts.claims()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenExpirationTime));

        claims.put("memberId", authentication.getPrincipal());

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(secretKeyFactory.create())
                .compact();
    }
}
