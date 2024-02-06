package com.smeem.auth.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smeme.server.model.SocialType;

public record SignInRequestDTO(
        @JsonProperty("social")
        SocialType socialType,
        String fcmToken
) {
}
