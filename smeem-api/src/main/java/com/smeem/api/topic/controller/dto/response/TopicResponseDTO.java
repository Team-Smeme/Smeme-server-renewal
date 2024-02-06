package com.smeem.api.topic.controller.dto.response;


import com.smeem.domain.topic.model.Topic;

public record TopicResponseDTO(
        Long topicId,
        String content
) {
    public static TopicResponseDTO of(Topic topic) {
        return new TopicResponseDTO(topic.getId(), topic.getContent());
    }
}
