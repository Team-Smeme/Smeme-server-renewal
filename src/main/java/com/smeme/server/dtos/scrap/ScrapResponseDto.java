package com.smeme.server.dtos.scrap;

import com.smeme.server.models.Scrap;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record ScrapResponseDto(
        @NonNull Long scrapId
) {

    static public ScrapResponseDto from(Scrap scrap) {
        return ScrapResponseDto.builder()
                .scrapId(scrap.getId())
                .build();
    }
}
