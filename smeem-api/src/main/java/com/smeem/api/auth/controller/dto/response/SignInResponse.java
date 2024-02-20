package com.smeem.api.auth.controller.dto.response;


import com.smeem.api.auth.service.dto.response.SignInServiceResponse;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;


@Builder(access = PRIVATE)
public record SignInResponse(
        String accessToken,
        String refreshToken,
        boolean isRegistered,
        boolean hasPlan
) {

        public static SignInResponse of(SignInServiceResponse response) {
                return SignInResponse.builder()
                        .accessToken(response.accessToken())
                        .refreshToken(response.refreshToken())
                        .isRegistered(response.isRegistered())
                        .hasPlan(response.hasPlan())
                        .build();
        }
}
