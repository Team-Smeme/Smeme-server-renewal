package com.smeem.api.member.service.dto.request;

import com.smeem.api.member.api.dto.request.MemberPushUpdateRequest;
import lombok.Builder;

import static lombok.AccessLevel.*;

@Builder(access = PRIVATE)
public record MemberPushUpdateServiceRequest(
        long memberId,
        boolean hasAlarm
) {
    public static MemberPushUpdateServiceRequest of(long memberId, MemberPushUpdateRequest request) {
        return MemberPushUpdateServiceRequest.builder()
                .memberId(memberId)
                .hasAlarm(request.hasAlarm())
                .build();
    }
}
