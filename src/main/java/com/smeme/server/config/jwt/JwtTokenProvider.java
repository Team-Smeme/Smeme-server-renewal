package com.smeme.server.config.jwt;


import com.smeme.server.config.ValueConfig;
import io.jsonwebtoken.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static com.smeme.server.config.jwt.JwtValidationType.*;
import static io.jsonwebtoken.Header.*;
import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static java.util.Base64.getEncoder;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final ValueConfig valueConfig;

    public String generateToken(Authentication authentication, Long tokenExpirationTime) {
        val now = new Date();

        val claims = Jwts.claims()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenExpirationTime));

        claims.put("memberId", authentication.getPrincipal());

        return Jwts.builder()
                .setHeaderParam(TYPE, JWT_TYPE)
                .setClaims(claims)
                .signWith(getSigningKey())
                .compact();
    }

    public JwtValidationType validateToken(String token) {
        try {
            getBody(token);
            return VALID_JWT;
        } catch (MalformedJwtException ex) {
            log.error(String.valueOf(INVALID_JWT_TOKEN));
            return INVALID_JWT_TOKEN;
        } catch (ExpiredJwtException ex) {
            log.error(String.valueOf(EXPIRED_JWT_TOKEN));
            return EXPIRED_JWT_TOKEN;
        } catch (UnsupportedJwtException ex) {
            log.error(String.valueOf(UNSUPPORTED_JWT_TOKEN));
            return UNSUPPORTED_JWT_TOKEN;
        } catch (IllegalArgumentException ex) {
            log.error(String.valueOf(EMPTY_JWT));
            return EMPTY_JWT;
        }
    }

    public Long getUserFromJwt(String token) {
        Claims claims = getBody(token);
        return Long.parseLong(claims.get("memberId").toString());
    }

    private SecretKey getSigningKey() {
        String encodedKey = getEncoder().encodeToString(valueConfig.getJWT_SECRET().getBytes());
        return hmacShaKeyFor(encodedKey.getBytes());
    }

    private Claims getBody(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
