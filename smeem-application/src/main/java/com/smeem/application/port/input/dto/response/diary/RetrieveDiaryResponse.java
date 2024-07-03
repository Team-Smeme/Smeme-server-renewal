package com.smeem.application.port.input.dto.response.diary;

import com.smeem.application.domain.diary.Diary;
import com.smeem.common.util.SmeemConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record RetrieveDiaryResponse(
        @Schema(description = "일기 id")
        long diaryId,
        @Schema(description = "일기 주제")
        String topic,
        @Schema(description = "일기 내용")
        String content,
        @Schema(description = "일기 작성일")
        String createdAt,
        @Schema(description = "일기 작성자 닉네임")
        String username
) {

        public static RetrieveDiaryResponse of(Diary diary) {
                return RetrieveDiaryResponse.builder()
                        .diaryId(diary.getId())
                        .topic(diary.getTopic() != null ? diary.getTopic().getContent() : "")
                        .content(diary.getContent())
                        .createdAt(SmeemConverter.toString(diary.getCreatedAt()))
                        .username(diary.getMember().getUsername())
                        .build();
        }

}

