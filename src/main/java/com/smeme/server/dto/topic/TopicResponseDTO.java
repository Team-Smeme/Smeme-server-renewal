package com.smeme.server.dto.topic;

import com.smeme.server.model.topic.Topic;

import io.swagger.v3.oas.annotations.media.Schema;

public record TopicResponseDTO(
	@Schema(description = "랜덤 주제 id", example = "1")
	Long topicId,

	@Schema(description = "랜덤 주제 내용", example = "가보고 싶은 해외 여행지가 있다면 소개해 주세요!")
	String content
) {
	public static TopicResponseDTO of (Topic topic) {
		return new TopicResponseDTO(topic.getId(), topic.getContent());
	}
}
