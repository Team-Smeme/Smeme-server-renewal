package com.smeme.server.dto.diary;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NonNull;

@Schema(description = "일기 생성 요청 DTO")
public record DiaryRequestDTO(
        @Schema(description = "일기 내용", example = "Hello Smeem")
        @NonNull
        String content,
        @Schema(description = "일기 랜덤주제 id", example = "1")
        Long topicId
) {
}
