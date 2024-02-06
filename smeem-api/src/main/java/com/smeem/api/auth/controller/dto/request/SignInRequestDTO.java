package com.smeem.api.auth.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smeem.domain.member.model.SocialType;

public record SignInRequestDTO(
        @JsonProperty("social")
        SocialType socialType,
        String fcmToken
) {
}
