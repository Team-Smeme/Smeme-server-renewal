package com.smeem.api.auth.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smeem.domain.member.model.SocialType;

public record SignInRequest(
        @JsonProperty("social")
        SocialType socialType,
        String fcmToken
) {
}
