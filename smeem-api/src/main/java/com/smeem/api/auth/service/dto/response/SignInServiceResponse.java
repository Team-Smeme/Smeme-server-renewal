package com.smeem.api.auth.service.dto.response;


import com.smeem.api.auth.jwt.SmeemToken;
import lombok.Builder;

import static lombok.AccessLevel.*;

@Builder(access = PRIVATE)
public record SignInServiceResponse(
        String accessToken,
        String refreshToken,
        boolean isRegistered,
        boolean hasPlan
) {
    public static SignInServiceResponse of(SmeemToken token, boolean isRegistered, boolean hasPlan) {
        return SignInServiceResponse.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .hasPlan(hasPlan)
                .isRegistered(isRegistered)
                .build();
    }
}
