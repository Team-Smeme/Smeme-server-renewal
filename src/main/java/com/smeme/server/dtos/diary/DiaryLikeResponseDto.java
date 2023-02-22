package com.smeme.server.dtos.diary;

import lombok.Builder;

@Builder
public record DiaryLikeResponseDto(
	boolean hasLike
) {
}
