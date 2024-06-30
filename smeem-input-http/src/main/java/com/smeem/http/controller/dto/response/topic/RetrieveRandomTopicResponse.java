package com.smeem.http.controller.dto.response.topic;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record RetrieveRandomTopicResponse(
        @Schema(description = "일기 주제 id")
        long topicId,
        @Schema(description = "주제 내용")
        String content
) {
}
