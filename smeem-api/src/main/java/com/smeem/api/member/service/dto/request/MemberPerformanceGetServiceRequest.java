package com.smeem.api.member.service.dto.request;

import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record MemberPerformanceGetServiceRequest(
        long memberId
) {

    public static MemberPerformanceGetServiceRequest of(long memberId) {
        return MemberPerformanceGetServiceRequest.builder()
                .memberId(memberId)
                .build();
    }
}
