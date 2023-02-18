package com.smeme.server.dtos.topic;

import com.smeme.server.models.Topic;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record TopicResponseDto(
        @NonNull Long id,
        @NonNull String content
) {

    static public TopicResponseDto from(Topic topic) {
        return TopicResponseDto.builder()
                .id(topic.getId())
                .content(topic.getContent())
                .build();
    }

}
