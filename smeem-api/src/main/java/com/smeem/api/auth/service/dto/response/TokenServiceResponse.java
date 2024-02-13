package com.smeem.api.auth.service.dto.response;

import com.smeem.api.auth.jwt.SmeemToken;
import lombok.Builder;

import static lombok.AccessLevel.*;


@Builder(access = PRIVATE)
public record TokenServiceResponse(
        String accessToken,
        String refreshToken
) {

    public static TokenServiceResponse of(SmeemToken token) {
        return new TokenServiceResponse(
                token.getAccessToken(),
                token.getRefreshToken()
        );
    }
}
