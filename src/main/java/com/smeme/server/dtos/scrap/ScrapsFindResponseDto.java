package com.smeme.server.dtos.scrap;

import lombok.Builder;
import lombok.NonNull;

import java.util.List;

@Builder
public record ScrapsFindResponseDto(
        @NonNull List<ScrapFindResponseDto> scraps
) {
}
