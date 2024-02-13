package com.smeem.api.auth.jwt;


import com.smeem.common.config.ValueConfig;
import com.smeem.common.exception.AuthException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static com.smeem.api.auth.jwt.JwtValidationType.*;
import static java.util.Base64.getEncoder;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

    private final SecretKeyFactory secretKeyFactory;

    public String generateToken(Authentication authentication, Long tokenExpirationTime) {
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
