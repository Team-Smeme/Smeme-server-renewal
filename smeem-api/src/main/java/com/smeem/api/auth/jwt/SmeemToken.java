package com.smeem.api.auth.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SmeemToken {

    private final String accessToken;
    private final String refreshToken;

    @Builder
    private SmeemToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static SmeemToken of(String accessToken, String refreshToken) {
        return SmeemToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
