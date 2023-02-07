package com.smeme.server.dtos.diary;

import com.smeme.server.models.Diary;
import lombok.Builder;

@Builder
public record DiaryCreateResponseDto(
        Long diaryId
) {

    public static DiaryCreateResponseDto from(Diary diary) {
        return DiaryCreateResponseDto.builder().diaryId(diary.getId()).build();
    }
}
