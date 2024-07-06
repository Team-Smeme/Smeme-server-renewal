package com.smeem.application.port.input.dto.response.diary;

import com.smeem.application.domain.diary.Diary;
import com.smeem.common.util.SmeemConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record RetrieveDiariesResponse(
        @Schema(description = "일기 정보")
        List<DiaryResponse> diaries,
        @Schema(description = "30일 전 작성한 일기의 존재 유무")
        boolean has30Past
) {

    public static RetrieveDiariesResponse of(List<Diary> diaries, boolean isExistPastAgo) {
        return RetrieveDiariesResponse.builder()
                .diaries(diaries.stream().map(DiaryResponse::of).toList())
                .has30Past(isExistPastAgo)
                .build();
    }

    @Builder(access = AccessLevel.PRIVATE)
    private record DiaryResponse(
            @Schema(description = "일기 id")
            long diaryId,
            @Schema(description = "일기 내용")
            String content,
            @Schema(description = "일기 작성일")
            String createdAt
    ) {

        private static DiaryResponse of(Diary diary) {
            return DiaryResponse.builder()
                    .diaryId(diary.getId())
                    .content(diary.getContent())
                    .createdAt(SmeemConverter.toString(diary.getCreatedAt()))
                    .build();
        }
    }
}


