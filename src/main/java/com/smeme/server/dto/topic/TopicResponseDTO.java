package com.smeme.server.dto.topic;

import com.smeme.server.model.topic.Topic;

public record TopicResponseDTO(
	Long topicId,
	String content
) {
	public static TopicResponseDTO of(Topic topic) {
		return new TopicResponseDTO(topic.getId(), topic.getContent());
	}
}
