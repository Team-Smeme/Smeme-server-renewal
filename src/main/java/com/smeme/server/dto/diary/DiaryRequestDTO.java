package com.smeme.server.dto.diary;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NonNull;

@Schema(description = "일기 생성 요청 DTO")
public record DiaryRequestDTO(
	@NonNull
	String content,
	Long topicId
) {
}
