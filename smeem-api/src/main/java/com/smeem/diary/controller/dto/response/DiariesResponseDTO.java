package com.smeem.diary.controller.dto.response;

import java.util.List;

import com.smeme.server.model.Diary;

import static com.smeme.server.util.Util.dateToString;

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


