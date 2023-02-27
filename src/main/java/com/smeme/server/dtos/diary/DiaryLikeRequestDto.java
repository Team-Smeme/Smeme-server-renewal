package com.smeme.server.dtos.diary;

import lombok.NonNull;

public record DiaryLikeRequestDto(
	@NonNull Long diaryId
) {
}
