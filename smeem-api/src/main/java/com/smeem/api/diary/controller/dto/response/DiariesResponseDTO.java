package com.smeem.api.diary.controller.dto.response;

import com.smeem.common.util.Util;
import com.smeem.domain.diary.model.Diary;

import java.util.List;


public record DiariesResponseDTO(
        List<DiaryDTO> diaries,
        boolean has30Past
) {
    public static DiariesResponseDTO of(List<Diary> diaries, boolean hasRemind) {
        return new DiariesResponseDTO(diaries.stream().map(DiaryDTO::of).toList(), hasRemind);
    }

    public record DiaryDTO(
            Long diaryId,
            String content,
            String createdAt
    ) {
        public static DiaryDTO of(Diary diary) {
            return new DiaryDTO(diary.getId(), diary.getContent(), Util.dateToString(diary.getCreatedAt()));
        }
    }
}


