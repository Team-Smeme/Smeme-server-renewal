package com.smeem.application.port.input.dto.request.diary;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NonNull;

public record WriteDiaryRequest(
        @Schema(description = "작성한 내용")
        @NonNull
        String content,
        @Schema(description = "일기 주제 id")
        Long topicId
) {
}
