package com.smeem.application.port.input.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smeem.application.domain.generic.LangType;
import com.smeem.application.domain.member.Member;
import com.smeem.application.domain.member.SocialType;
import io.swagger.v3.oas.annotations.media.Schema;

public record SignInRequest(
        @Schema(description = "가입하려는 소셜서비스")
        @JsonProperty("social")
        SocialType socialType,
        @Schema(description = "회원의 FCM 토큰")
        String fcmToken
) {

        public Member toDomain(String socialId) {
                return new Member(socialType, socialId, fcmToken);
        }
}
