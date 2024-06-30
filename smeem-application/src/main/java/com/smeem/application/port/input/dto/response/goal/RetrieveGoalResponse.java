package com.smeem.application.port.input.dto.response.goal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record RetrieveGoalResponse(
        @Schema(description = "학습목표 이름")
        String name,
        @Schema(description = "학습목표 제목")
        String title,
        @Schema(description = "학습목표 방법")
        String way,
        @Schema(description = "학습목표 세부내용")
        String detail
) {
}
