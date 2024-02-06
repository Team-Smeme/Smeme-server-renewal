package com.smeem.api.auth.controller.dto.response.token;


public record TokenVO(String accessToken, String refreshToken) {
    public static TokenVO of(String accessToken, String refreshToken) {
        return new TokenVO(accessToken, refreshToken);
    }
}
