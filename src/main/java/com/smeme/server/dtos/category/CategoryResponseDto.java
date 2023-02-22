package com.smeme.server.dtos.category;

import com.smeme.server.models.Category;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record CategoryResponseDto(
	@NonNull Long id,
	@NonNull String content
) {

	static public CategoryResponseDto from(Category category) {
		return CategoryResponseDto.builder()
			.id(category.getId())
			.content(category.getContent())
			.build();
	}
}
