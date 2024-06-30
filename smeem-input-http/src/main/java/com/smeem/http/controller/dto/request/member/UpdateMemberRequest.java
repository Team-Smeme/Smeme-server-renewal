package com.smeem.http.controller.dto.request.member;

import com.smeem.http.validator.ValidUsername;
import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateMemberRequest(
        @Schema(description = "회원의 닉네임")
        @ValidUsername
        String username,
        @Schema(description = "회원의 정보 수신 동의 여부")
        Boolean termAccepted
) {
}
