package com.smeem.application.port.input.dto.response.member;

import com.smeem.application.domain.badge.Badge;
import com.smeem.application.port.input.dto.response.badge.AcquiredBadgeResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record UpdateMemberResponse(
        @Schema(description = "획득한 배지 정보")
        List<AcquiredBadgeResponse> badges
) {

        public static UpdateMemberResponse of(List<Badge> badges) {
              return UpdateMemberResponse.builder()
                      .badges(badges.stream().map(AcquiredBadgeResponse::from).toList())
                      .build();
        }
}
