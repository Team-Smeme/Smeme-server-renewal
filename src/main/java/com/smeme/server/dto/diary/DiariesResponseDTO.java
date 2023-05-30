package com.smeme.server.dto.diary;

import java.util.List;

import com.smeme.server.model.Diary;
import com.smeme.server.util.Util;
import lombok.AllArgsConstructor;

public record DiariesResponseDTO(
	List<DiaryDTO> diaries,
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
	Long diaryId,
	String content,
	String createdAt
) {
	public static DiaryDTO of(Diary diary) {
		return new DiaryDTO(diary.getId(),
			diary.getContent(),
			Util.transferDateTimeToString(diary.getCreatedAt()));
	}
}
