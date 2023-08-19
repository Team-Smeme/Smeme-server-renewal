package com.smeme.server.dto.diary;

import lombok.NonNull;

public record DiaryRequestDTO(
	@NonNull
	String content,
	Long topicId
) {
}
