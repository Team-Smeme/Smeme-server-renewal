package com.smeem.api.member.service.dto.request;

import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record MemberVisitUpdateRequest(
        long memberId
) {

    public static MemberVisitUpdateRequest of(long memberId) {
        return MemberVisitUpdateRequest.builder()
                .memberId(memberId)
                .build();
    }
}
