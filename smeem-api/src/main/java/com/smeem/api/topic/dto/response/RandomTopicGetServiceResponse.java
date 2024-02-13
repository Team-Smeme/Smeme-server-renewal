package com.smeem.api.topic.dto.response;

import com.smeem.domain.topic.model.Topic;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record RandomTopicGetServiceResponse(
        long topicId,
        String content
) {

    public static RandomTopicGetServiceResponse of(Topic topic) {
        return RandomTopicGetServiceResponse.builder()
                .topicId(topic.getId())
                .content(topic.getContent())
                .build();
    }
}
