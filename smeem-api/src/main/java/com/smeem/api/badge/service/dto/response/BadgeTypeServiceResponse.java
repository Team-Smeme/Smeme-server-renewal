package com.smeem.api.badge.service.dto.response;

import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.badge.model.BadgeType;
import com.smeem.domain.member.model.MemberBadge;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record BadgeTypeServiceResponse(
        BadgeType badgeType,
        String badgeTypeName,
        List<BadgeBaseServiceResponse> badges
) {
    public static BadgeTypeServiceResponse of(
            BadgeType type,
            List<Badge> badges,
            List<MemberBadge> memberBadges) {
        return BadgeTypeServiceResponse.builder()
                .badgeType(type)
                .badgeTypeName(type.getDescription())
                .badges(getBadges(badges, memberBadges))
                .build();
    }

    private static List<BadgeBaseServiceResponse> getBadges(
            List<Badge> badges,
            List<MemberBadge> memberBadges
    ) {
        return badges.stream()
                .map(badge -> BadgeBaseServiceResponse.of(badge, memberBadges)).toList();
    }

}
