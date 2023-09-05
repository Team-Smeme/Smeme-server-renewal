package com.smeme.server.dto.member;

import com.smeme.server.model.badge.Badge;
import com.smeme.server.model.badge.MemberBadge;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record MemberUpdateResponseDTO(
        List<BadgeDTO> badges
) {
    public static MemberUpdateResponseDTO of(List<Badge> badges) {
        return new MemberUpdateResponseDTO(badges.stream().map(badge -> new BadgeDTO(badge.getName(), badge.getImageUrl())).toList());
    }

    record BadgeDTO(
            @Schema(description = "뱃지 이름", example = "웰컴뱃지")
            String name,

            @Schema(description = "뱃지 이미지 url", example = "https://m.s3.ap-northeast-2.amazonaws.com/badge/welcome.png")
            String imageUrl
    ) {

    }

}

