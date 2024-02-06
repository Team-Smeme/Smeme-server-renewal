package com.smeem.member.controller.dto.response;

import com.smeme.server.dto.badge.AcquiredBadgeResponseDTO;
import com.smeme.server.model.badge.Badge;

import java.util.List;

public record MemberUpdateResponseDTO(
        List<AcquiredBadgeResponseDTO> badges
) {
    public static MemberUpdateResponseDTO of(List<Badge> badges) {
        return new MemberUpdateResponseDTO(badges.stream().map(AcquiredBadgeResponseDTO::of).toList());
    }
}
