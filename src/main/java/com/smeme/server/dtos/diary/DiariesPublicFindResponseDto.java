package com.smeme.server.dtos.diary;

import java.util.List;

public record DiariesPublicFindResponseDto(
        List<DiaryPublicFindResponseDto> diaries
) {
}
