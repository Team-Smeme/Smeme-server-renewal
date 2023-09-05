package com.smeme.server.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberUpdateRequestDTO(
        @Schema(description = "회원 이름", example = "홍길동")
        @ValidUsername
        String username,

        @Schema(description = "정책 동의 여부", example = "false")
        Boolean termAccepted
) {
}
