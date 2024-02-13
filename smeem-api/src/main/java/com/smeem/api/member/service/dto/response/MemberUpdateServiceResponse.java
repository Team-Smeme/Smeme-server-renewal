package com.smeem.api.member.service.dto.response;

import com.smeem.api.badge.controller.dto.response.AcquiredBadgeResponse;
import com.smeem.api.badge.service.dto.response.AcquiredBadgeServiceResponse;
import com.smeem.api.badge.service.dto.response.BadgeServiceResponse;
import com.smeem.domain.badge.model.Badge;
import lombok.Builder;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record MemberUpdateServiceResponse(
        List<AcquiredBadgeServiceResponse> badges
) {

    public static MemberUpdateServiceResponse of(List<Badge> badges) {
        return MemberUpdateServiceResponse.builder()
                .badges(badges.stream().map(AcquiredBadgeServiceResponse::of).toList())
                .build();
    }
}
