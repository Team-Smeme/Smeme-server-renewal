package com.smeem.api.badge.service.dto.response;

import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.badge.model.BadgeType;
import com.smeem.domain.member.model.MemberBadge;
import lombok.Builder;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record BadgeBaseServiceResponse(
        String name,
        BadgeType type,
        String imageUrl
) {

    public static BadgeBaseServiceResponse of(Badge badge, List<MemberBadge> memberBadges) {
        return BadgeBaseServiceResponse.builder()
                .name(badge.getName())
                .type(badge.getType())
                .imageUrl(getImageUrl(badge, memberBadges))
                .build();
    }

    private static String getImageUrl(Badge badge, List<MemberBadge> memberBadges) {
        return hasBadge(badge, memberBadges) ? badge.getBadgeImage().getImageUrl() : badge.getBadgeImage().getGrayImageUrl();
    }

    private static boolean hasBadge(Badge badge, List<MemberBadge> memberBadges) {
        return memberBadges.stream().anyMatch(memberBadge -> memberBadge.getBadge().equals(badge));
    }
}
