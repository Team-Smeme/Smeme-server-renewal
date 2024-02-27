package com.smeem.api.test.service.dto.request;

import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record TestPushAlarmServiceRequest(
        long memberId
) {

    public static TestPushAlarmServiceRequest of(long memberId) {
        return TestPushAlarmServiceRequest.builder()
                .memberId(memberId)
                .build();
    }
}
