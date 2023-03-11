package com.smeme.server.config.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final int ACCESS_TOKEN_EXPIRATION_TIME = 7200000; // 2시간

    private static final int REFRESH_TOKEN_EXPIRATION_TIME = 1209600000; // 2주

    private final Environment env;

    public String generateAccessToken(Authentication authentication) {
        return Jwts.builder()
                .setSubject(String.valueOf(authentication.getPrincipal()))
                .setIssuedAt(new Date())
                .setExpiration(getExpirationDate(ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, getJwtSecretKey())
                .compact();
    }

    public String generateRefreshToken(Authentication authentication) {
        return Jwts.builder()
                .setSubject(String.valueOf(authentication.getPrincipal()))
                .setIssuedAt(new Date())
                .setExpiration(getExpirationDate(REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, getJwtSecretKey())
                .compact();
    }
    public Long getUserFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getJwtSecretKey())
                .parseClaimsJws(token)
                .getBody();
        return Long.valueOf(claims.getSubject());
    }

    private Date getExpirationDate(int tokenExpirationTime) {
        Date currentDate = new Date();
        return new Date(currentDate.getTime() + tokenExpirationTime);
    }

    public JwtValidationType validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(getJwtSecretKey()).parseClaimsJws(token);
            return JwtValidationType.VALID_JWT;
        } catch (SignatureException ex) {
            log.error(String.valueOf(JwtValidationType.INVALID_JWT_SIGNATURE));
            return JwtValidationType.INVALID_JWT_SIGNATURE;
        } catch (MalformedJwtException ex) {
            log.error(String.valueOf(JwtValidationType.INVALID_JWT_TOKEN));
            return JwtValidationType.INVALID_JWT_TOKEN;
        } catch (ExpiredJwtException ex) {
            log.error(String.valueOf(JwtValidationType.EXPIRED_JWT_TOKEN));
            return JwtValidationType.EXPIRED_JWT_TOKEN;
        } catch (UnsupportedJwtException ex) {
            log.error(String.valueOf(JwtValidationType.UNSUPPORTED_JWT_TOKEN));
            return JwtValidationType.UNSUPPORTED_JWT_TOKEN;
        } catch (IllegalArgumentException ex) {
            log.error(String.valueOf(JwtValidationType.EMPTY_JWT));
            return JwtValidationType.EMPTY_JWT;
        }
    }

    private String getJwtSecretKey() {
        return env.getProperty("jwt.secret");
    }

}
