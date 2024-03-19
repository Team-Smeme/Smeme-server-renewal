package com.smeem.api.auth.service.dto.request;

import com.smeem.api.auth.api.dto.request.SignInRequest;
import com.smeem.domain.member.model.SocialType;
import lombok.Builder;

import static lombok.AccessLevel.*;


@Builder(access = PRIVATE)
public record SignInServiceRequest(
        SocialType socialType,
        String fcmToken
) {
    public static SignInServiceRequest of(SignInRequest request) {
        return SignInServiceRequest
                .builder()
                .socialType(request.socialType())
                .fcmToken(request.fcmToken())
                .build();
    }
}
