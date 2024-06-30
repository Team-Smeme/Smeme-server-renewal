package com.smeem.http.controller.dto.request.member;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateMemberHasPushAlarmRequest(
        @Schema(description = "동의 여부")
        boolean hasAlarm
) {
}
