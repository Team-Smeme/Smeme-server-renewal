package com.smeme.server.dtos.scrap;

import com.smeme.server.models.Scrap;
import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
public record ScrapFindResponseDto(
        @NonNull Long id,
        @NonNull String paragraph,
        @NonNull String createdAt
) {

    static public ScrapFindResponseDto from(Scrap scrap) {
        return ScrapFindResponseDto.builder()
                .id(scrap.getId())
                .paragraph(scrap.getParagraph())
                .createdAt(getCreatedAt(scrap.getCreatedAt()))
                .build();
    }

    static private String getCreatedAt(LocalDateTime createdAt) {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
