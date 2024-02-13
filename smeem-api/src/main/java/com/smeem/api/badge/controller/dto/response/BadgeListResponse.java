package com.smeem.api.badge.controller.dto.response;

import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.badge.model.BadgeType;
import com.smeem.domain.member.model.MemberBadge;
import lombok.*;

import java.util.List;
import java.util.Map;

public record BadgeListResponse(
        List<BadgeTypeResponse> badgeTypes
) {
    public static BadgeListResponse of(Map<BadgeType, List<Badge>> badgeMap, List<MemberBadge> memberBadges) {
        val badgeTypes = getBadgesType(badgeMap, memberBadges);
        return new BadgeListResponse(badgeTypes);
    }

    private static List<BadgeTypeResponse> getBadgesType(Map<BadgeType, List<Badge>> badgeMap, List<MemberBadge> memberBadges) {
        return badgeMap.keySet().stream()
                .map(type -> BadgeTypeResponse.of(type, badgeMap.get(type), memberBadges))
                .toList();
    }

    @Builder
    public record BadgeTypeResponse(
            BadgeType badgeType,
            String badgeTypeName,
            List<BadgeResponseDTO> badges
    ) {
        public static BadgeTypeResponse of(BadgeType type, List<Badge> badges, List<MemberBadge> memberBadges) {
            return BadgeTypeResponse.builder()
                    .badgeType(type)
                    .badgeTypeName(type.getDescription())
                    .badges(getBadges(badges, memberBadges))
                    .build();
        }

        private static List<BadgeResponseDTO> getBadges(List<Badge> badges, List<MemberBadge> memberBadges) {
            return badges.stream()
                    .map(badge -> BadgeResponseDTO.of(badge, memberBadges))
                    .toList();
        }
    }

    @Builder
    public record BadgeResponseDTO(
            String name,
            BadgeType type,
            String imageUrl
    ) {

        private static BadgeResponseDTO of(Badge badge, List<MemberBadge> memberBadges) {
            return BadgeResponseDTO.builder()
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
}