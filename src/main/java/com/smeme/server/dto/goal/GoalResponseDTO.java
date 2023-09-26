package com.smeme.server.dto.goal;

import io.swagger.v3.oas.annotations.media.Schema;

public record GoalResponseDTO(
        @Schema(description = "학습 목표 이름", example = "현지 언어 체득")
        String name,
        @Schema(description = "학습 목표 방식", example = "주 5회 이상 오늘 하루를 돌아보는 일기 작성하기")
        String way,
        @Schema(description = "학습 목표 내용", example = "사전 없이 일기 완성\nsmeem 연속 일기 배지 획득")
        String detail
) {
}
