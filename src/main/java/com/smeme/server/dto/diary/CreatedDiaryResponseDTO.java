package com.smeme.server.dto.diary;

import java.util.List;

import com.smeme.server.model.badge.Badge;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "일기 생성 응답 DTO")
public record CreatedDiaryResponseDTO(
        @Schema(description = "생성된 일기 id", example = "1")
        Long diaryId,
        @Schema(description = "획득한 뱃지 리스트")
        List<BadgeDTO> badges
) {
    public static CreatedDiaryResponseDTO of(Long diaryId, List<Badge> badges) {
        return new CreatedDiaryResponseDTO(diaryId, badges.stream().map(BadgeDTO::of).toList());
    }

    record BadgeDTO(
            @Schema(description = "뱃지 이름", example = "웰컴 뱃지")
            String name,
            @Schema(description = "뱃지 이미지 url", example = "image-url")
            String imageUrl
    ) {
        public static BadgeDTO of(Badge badge) {
            return new BadgeDTO(badge.getName(), badge.getImageUrl());
        }
    }
}


