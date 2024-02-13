package com.smeem.api.test.dto.request;

import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record TestPushAlarmRequest(
        long memberId
) {

    public static TestPushAlarmRequest of(long memberId) {
        return TestPushAlarmRequest.builder()
                .memberId(memberId)
                .build();
    }
}
