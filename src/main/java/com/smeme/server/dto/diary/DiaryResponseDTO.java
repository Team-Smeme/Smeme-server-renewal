package com.smeme.server.dto.diary;

import static java.util.Objects.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.smeme.server.model.Correction;
import com.smeme.server.model.Diary;
import com.smeme.server.model.topic.Topic;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record DiaryResponseDTO(
	Long diaryId,
	String topic,
	String content,
	String createdAt,
	String username,
	List<CorrectionDTO> corrections
) {
	public static DiaryResponseDTO of(Diary diary) {
		return DiaryResponseDTO.builder()
			.diaryId(diary.getId())
			.topic(getTopic(diary.getTopic()))
			.content(diary.getContent())
			.createdAt(getCreatedAt(diary.getCreatedAt()))
			.username(diary.getMember().getUsername())
			.corrections(getCorrections(diary.getCorrections()))
			.build();
	}

	private static String getTopic(Topic topic) {
		return nonNull(topic) ? topic.getContent() : "";
	}

	private static String getCreatedAt(LocalDateTime createdAt) {
		return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}

	private static List<CorrectionDTO> getCorrections(List<Correction> corrections) {
		return corrections.stream().map(CorrectionDTO::of).toList();
	}
}

record CorrectionDTO(
	Long correctionId,
	String before,
	String after
) {
	static CorrectionDTO of(Correction correction) {
		return new CorrectionDTO(correction.getId(), correction.getBeforeSentence(), correction.getAfterSentence());
	}
}
