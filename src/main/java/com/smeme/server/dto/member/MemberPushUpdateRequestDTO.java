package com.smeme.server.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 푸시알람 동의여부 수정 요청")
public record MemberPushUpdateRequestDTO(

        @Schema(description = "알람 여부", example = "true")
        boolean hasAlarm
) {
}
