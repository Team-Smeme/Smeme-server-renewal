package com.smeem.api.member.service.dto.request;

import com.smeem.api.member.api.dto.request.MemberPushUpdateRequest;
import lombok.Builder;

import static lombok.AccessLevel.*;

@Builder(access = PRIVATE)
public record MemberPushUpdateServiceRequest(
        boolean hasAlarm
) {
    public static MemberPushUpdateServiceRequest of(MemberPushUpdateRequest request) {
        return MemberPushUpdateServiceRequest.builder()
                .hasAlarm(request.hasAlarm())
                .build();
    }
}
