package com.smeem.api.diary.service.dto.response;

import com.smeem.common.config.ValueConfig;
import com.smeem.domain.diary.model.Diary;
import com.smeem.domain.member.model.Member;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record DiaryListGetServiceResponse(
        List<DiaryServiceResponse> diaries,
        boolean hasDiaryWrittenAgo
) {

    public static DiaryListGetServiceResponse of(List<Diary> diaries, Member member, ValueConfig valueConfig) {
        return DiaryListGetServiceResponse.builder()
                .diaries(diaries.stream().map(DiaryServiceResponse::of).toList())
                .hasDiaryWrittenAgo(member.hasDiaryWrittenAgo(valueConfig.getDURATION_REMIND()))
                .build();
    }

    @Builder(access = PRIVATE)
    public record DiaryServiceResponse(
            long diaryId,
            String content,
            LocalDateTime createdAt
    ) {

        public static DiaryServiceResponse of(Diary diary) {
            return DiaryServiceResponse.builder()
                    .diaryId(diary.getId())
                    .content(diary.getContent())
                    .createdAt(diary.createdAt)
                    .build();
        }
    }
}


