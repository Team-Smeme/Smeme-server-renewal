package com.smeem.api.member.service.dto.request;

import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record MemberPlanGetServiceRequest(
        long memberId
) {

    public static MemberPlanGetServiceRequest of(long memberId) {
        return MemberPlanGetServiceRequest.builder()
                .memberId(memberId)
                .build();
    }
}
