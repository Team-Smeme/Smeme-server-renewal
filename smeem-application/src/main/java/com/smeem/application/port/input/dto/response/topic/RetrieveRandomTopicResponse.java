package com.smeem.application.port.input.dto.response.topic;

import com.smeem.application.domain.topic.Topic;
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

        public static RetrieveRandomTopicResponse of(Topic topic) {
                return RetrieveRandomTopicResponse.builder()
                        .topicId(topic.getId())
                        .content(topic.getContent())
                        .build();
        }
}
