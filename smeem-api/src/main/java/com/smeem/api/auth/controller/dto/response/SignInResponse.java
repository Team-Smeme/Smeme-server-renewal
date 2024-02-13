package com.smeem.api.auth.controller.dto.response;


import com.smeem.api.auth.jwt.SmeemToken;
import com.smeem.api.auth.service.dto.response.SignInServiceResponse;
import lombok.Builder;


@Builder
public record SignInResponse(
        String accessToken,
        String refreshToken,
        boolean isRegistered,
        boolean hasPlan
) {

        public static SignInResponse of(SignInServiceResponse response) {
                return new SignInResponse(
                        response.accessToken(),
                        response.refreshToken(),
                        response.isRegistered(),
                        response.hasPlan());
        }

}
