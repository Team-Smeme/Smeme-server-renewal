package com.smeem.api.support.jwt;

import com.smeem.api.domain.member.Member;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.smeem.common.config.ValueConfig.ACCESS_TOKEN_EXPIRATION_TIME;
import static com.smeem.common.config.ValueConfig.REFRESH_TOKEN_EXPIRATION_TIME;

@Component
@RequiredArgsConstructor
public class TokenGenerator {

    private final SecretKeyFactory secretKeyFactory;

    public String generateAccessToken(Member member) {
        val authentication = UserAuthentication.create(member.id());
        return generateToken(authentication, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String generateRefreshToken(Member member) {
        val authentication = UserAuthentication.create(member.id());
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
