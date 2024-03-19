package com.smeem.api.member.api.dto.response;


import com.smeem.api.badge.api.dto.response.AcquiredBadgeResponse;
import com.smeem.api.member.service.dto.response.MemberUpdateServiceResponse;
import lombok.Builder;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record MemberUpdateResponse(
        List<AcquiredBadgeResponse> badges
) {
    public static MemberUpdateResponse from(
            MemberUpdateServiceResponse response
    ) {
        return MemberUpdateResponse
                .builder()
                .badges(response.badges().stream().map(AcquiredBadgeResponse::from).toList())
                .build();
    }
}
