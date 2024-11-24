package com.smeem.application.port.input.dto.response.diary;

import com.smeem.application.domain.diary.Correction;
import com.smeem.application.domain.diary.Diary;
import com.smeem.application.domain.member.Member;
import com.smeem.application.domain.topic.Topic;
import com.smeem.common.util.SmeemConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

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
        String username,
        @Schema(description = "코칭 결과 정보")
        List<Correction> corrections,
        @Schema(description = "코칭 횟수")
        int correctionCount,
        @Schema(description = "코칭 최대 횟수")
        int correctionMaxCount
) {
        public static RetrieveDiaryResponse of(
                @NotNull Diary diary,
                Topic topic,
                @NotNull Member member,
                @NotNull List<Correction> corrections,
                int correctionCount
        ) {
                return RetrieveDiaryResponse.builder()
                        .diaryId(diary.getId())
                        .topic(topic != null ? topic.getContent() : "")
                        .content(diary.getContent())
                        .createdAt(SmeemConverter.toString(diary.getCreatedAt()))
                        .username(member.getUsername())
                        .corrections(corrections)
                        .correctionCount(correctionCount)
                        .correctionMaxCount(1)
                        .build();
        }
}

