package com.smeem.api.member.controller.dto.response;


import com.smeem.api.badge.controller.dto.response.AcquiredBadgeResponseDTO;
import com.smeem.domain.badge.model.Badge;

import java.util.List;

public record MemberUpdateResponseDTO(
        List<AcquiredBadgeResponseDTO> badges
) {
    public static MemberUpdateResponseDTO of(List<Badge> badges) {
        return new MemberUpdateResponseDTO(badges.stream().map(AcquiredBadgeResponseDTO::of).toList());
    }
}
