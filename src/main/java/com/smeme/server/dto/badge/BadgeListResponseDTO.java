package com.smeme.server.dto.badge;

import com.smeme.server.model.badge.Badge;
import com.smeme.server.model.badge.BadgeType;
import com.smeme.server.model.badge.MemberBadge;
import lombok.Builder;

import java.util.List;

public record BadgeListResponseDTO(
        List<BadgeResponseDTO> badges
) {
    public static BadgeListResponseDTO of(List<Badge> badges, List<MemberBadge> memberBadges) {
        return new BadgeListResponseDTO(badges.stream()
                .map(badge -> BadgeResponseDTO.of(badge, memberBadges))
                .toList());
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