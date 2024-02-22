package com.smeem.api.badge.service.dto.response;


import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.badge.model.BadgeType;
import com.smeem.domain.member.model.MemberBadge;
import lombok.Builder;
import lombok.val;

import java.util.List;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record BadgeListServiceResponse(
        List<BadgeTypeServiceResponse> badgeTypes
) {
    public static BadgeListServiceResponse of(Map<BadgeType, List<Badge>> badgeMap, List<MemberBadge> memberBadges) {
        val badgeTypes = getBadgesType(badgeMap, memberBadges);
        return BadgeListServiceResponse.builder()
                .badgeTypes(badgeTypes)
                .build();
    }

    private static List<BadgeTypeServiceResponse> getBadgesType(Map<BadgeType, List<Badge>> badgeMap, List<MemberBadge> memberBadges) {
        return badgeMap.keySet().stream()
                .map(type -> BadgeTypeServiceResponse.of(type, badgeMap.get(type), memberBadges))
                .toList();
    }
}
