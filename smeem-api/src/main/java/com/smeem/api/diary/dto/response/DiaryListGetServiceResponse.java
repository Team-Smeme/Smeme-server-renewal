package com.smeem.api.diary.dto.response;

import com.smeem.common.config.ValueConfig;
import com.smeem.common.util.Util;
import com.smeem.domain.diary.model.Diary;
import com.smeem.domain.member.model.Member;
import lombok.Builder;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record DiaryListGetServiceResponse(
        List<DiaryResponse> diaries,
        boolean has30Past
) {
    public static DiaryListGetServiceResponse of(List<Diary> diaries, Member member, ValueConfig valueConfig) {
        return DiaryListGetServiceResponse.builder()
                .diaries(diaries.stream().map(DiaryResponse::of).toList())
                .has30Past(member.hasDiaryWrittenAgo(valueConfig.getDURATION_REMIND()))
                .build();
    }

    @Builder(access = PRIVATE)
    public record DiaryResponse(
            long diaryId,
            String content,
            String createdAt
    ) {
        public static DiaryResponse of(Diary diary) {
            return DiaryResponse.builder()
                    .diaryId(diary.getId())
                    .content(diary.getContent())
                    .createdAt(Util.transferToLocalDateTime(diary.getCreatedAt()))
                    .build();
        }
    }
}


