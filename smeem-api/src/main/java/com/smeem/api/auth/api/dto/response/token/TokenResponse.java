package com.smeem.api.auth.api.dto.response.token;

import com.smeem.api.auth.service.dto.response.TokenServiceResponse;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record TokenResponse(
        String accessToken,
        String refreshToken
) {

    public static TokenResponse from(TokenServiceResponse response) {
        return TokenResponse.builder()
                .accessToken(response.accessToken())
                .refreshToken(response.refreshToken())
                .build();
    }
}
