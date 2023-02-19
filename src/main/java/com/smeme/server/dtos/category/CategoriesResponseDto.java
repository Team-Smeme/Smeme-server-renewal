package com.smeme.server.dtos.category;

import java.util.List;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record CategoriesResponseDto(
	@NonNull List<CategoryResponseDto> categories
) {
}
