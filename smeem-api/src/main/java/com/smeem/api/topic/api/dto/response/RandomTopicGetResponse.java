package com.smeem.api.topic.api.dto.response;

import com.smeem.api.topic.service.dto.response.RandomTopicGetServiceResponse;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record RandomTopicGetResponse(
        long topicId,
        String content
) {

    public static RandomTopicGetResponse from(RandomTopicGetServiceResponse response) {
        return RandomTopicGetResponse.builder()
                .topicId(response.topicId())
                .content(response.content())
                .build();
    }
}
