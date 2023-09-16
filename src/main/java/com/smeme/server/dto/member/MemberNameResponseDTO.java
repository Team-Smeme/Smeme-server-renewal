package com.smeme.server.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberNameResponseDTO(
        @Schema(description = "회원 이름 존재 여부", example = "true")
        boolean isExist
) {
}
