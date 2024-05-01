package com.smeem.api.member.api.dto.response;

import com.smeem.api.member.service.dto.response.MemberPerformanceGetServiceResponse;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record MemberPerformanceGetResponse(
        int visitDays,
        int diaryCount,
        int diaryComboCount,
        int badgeCount
) {

    public static MemberPerformanceGetResponse from(MemberPerformanceGetServiceResponse response) {
        return MemberPerformanceGetResponse.builder()
                .visitDays(response.visitDays())
                .diaryCount(response.diaryCount())
                .diaryComboCount(response.diaryComboCount())
                .badgeCount(response.badgeCount())
                .build();
    }
}
