package com.smeme.server.dto.diary;

import java.util.List;

import com.smeme.server.model.Diary;
import com.smeme.server.util.Util;

import io.swagger.v3.oas.annotations.media.Schema;

public record DiariesResponseDTO(
	@Schema(description = "일기 정보 리스트")
	List<DiaryDTO> diaries,

	@Schema(description = "30일 전 일기 존재 여부", example = "true")
	boolean has30Past
) {
	public static DiariesResponseDTO of(List<Diary> diaries, boolean has30Past) {
		return new DiariesResponseDTO(
			diaries.stream().map(DiaryDTO::of).toList(),
			has30Past
		);
	}
}

record DiaryDTO(
	@Schema(description = "일기 id", example = "1")
	Long diaryId,

	@Schema(description = "일기 내용", example = "Hello Smeem")
	String content,

	@Schema(description = "일기 작성 일자", example = "2023-08-01 14:00")
	String createdAt
) {
	public static DiaryDTO of(Diary diary) {
		return new DiaryDTO(diary.getId(),
			diary.getContent(),
			Util.transferDateTimeToString(diary.getCreatedAt()));
	}
}
