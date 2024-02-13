package com.smeem.api.diary.service.dto.response;

import static java.util.Objects.*;


import com.smeem.common.util.Util;
import com.smeem.domain.diary.model.Diary;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record DiaryGetServiceResponse(
        long diaryId,
        String topic,
        String content,
        String createdAt,
        String username
) {
    public static DiaryGetServiceResponse of(Diary diary) {
        return DiaryGetServiceResponse.builder()
                .diaryId(diary.getId())
                .topic(nonNull(diary.getTopic()) ? diary.getTopic().getContent() : "")
                .content(diary.getContent())
                .createdAt(Util.transferToLocalDateTime(diary.getCreatedAt()))
                .username(diary.getMember().getUsername())
                .build();
    }

}

