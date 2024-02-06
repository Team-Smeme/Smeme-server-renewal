package com.smeem.badge.controller.dto.response;

import com.smeme.server.model.badge.Badge;
import com.smeme.server.model.badge.BadgeType;
import com.smeme.server.model.badge.MemberBadge;
import lombok.*;

import java.util.List;
import java.util.Map;

public record BadgeListResponseDTO(
        List<BadgeTypeResponseDTO> badgeTypes
) {
    public static BadgeListResponseDTO of(Map<BadgeType, List<Badge>> badgeMap, List<MemberBadge> memberBadges) {
        val badgeTypes = getBadgesType(badgeMap, memberBadges);
        return new BadgeListResponseDTO(badgeTypes);
    }

    private static List<BadgeTypeResponseDTO> getBadgesType(Map<BadgeType, List<Badge>> badgeMap, List<MemberBadge> memberBadges) {
        return badgeMap.keySet().stream()
                .map(type -> BadgeTypeResponseDTO.of(type, badgeMap.get(type), memberBadges))
                .toList();
    }

    @Builder
    public record BadgeTypeResponseDTO(
            BadgeType badgeType,
            String badgeTypeName,
            List<BadgeResponseDTO> badges
    ) {
        public static BadgeTypeResponseDTO of(BadgeType type, List<Badge> badges, List<MemberBadge> memberBadges) {
            return BadgeTypeResponseDTO.builder()
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
            return hasBadge(badge, memberBadges) ? badge.getImageUrl() : badge.getGrayImageUrl();
        }

        private static boolean hasBadge(Badge badge, List<MemberBadge> memberBadges) {
            return memberBadges.stream().anyMatch(memberBadge -> memberBadge.getBadge().equals(badge));
        }
    }
}