package com.smeme.server.dto.diary;

import static com.smeme.server.util.Util.dateToString;
import static java.util.Objects.*;

import java.util.List;

import com.smeme.server.model.Diary;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record DiaryResponseDTO(
        Long diaryId,
        String topic,
        String content,
        String createdAt,
        String username
) {
    public static DiaryResponseDTO of(Diary diary) {
        return DiaryResponseDTO.builder()
                .diaryId(diary.getId())
                .topic(nonNull(diary.getTopic()) ? diary.getTopic().getContent() : "")
                .content(diary.getContent())
                .createdAt(dateToString(diary.getCreatedAt()))
                .username(diary.getMember().getUsername())
                .build();
    }

}

