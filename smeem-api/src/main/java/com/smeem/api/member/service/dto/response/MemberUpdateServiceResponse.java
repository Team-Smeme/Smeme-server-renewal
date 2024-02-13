package com.smeem.api.member.service.dto.response;

import com.smeem.api.badge.controller.dto.response.BadgeResponse;
import com.smeem.domain.badge.model.Badge;
import lombok.Builder;

import java.util.List;

import static lombok.AccessLevel.*;

@Builder(access = PRIVATE)
public record MemberUpdateServiceResponse(
        List<BadgeResponse> badges
) {

    public static MemberUpdateServiceResponse of(List<Badge> badges) {
        return MemberUpdateServiceResponse.builder()
                .badges(badges.stream().map(BadgeResponse::of).toList()
                .build();
    }
}
