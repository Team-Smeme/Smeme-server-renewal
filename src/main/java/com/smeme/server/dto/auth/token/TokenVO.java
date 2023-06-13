package com.smeme.server.dto.auth.token;


public record TokenVO(String accessToken, String refreshToken) {
    public static TokenVO of(String accessToken, String refreshToken) {
        return new TokenVO(accessToken, refreshToken);
    }
}
