package com.smeem.api.member.service.dto.response;

import com.smeem.domain.member.model.Member;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record MemberPerformanceGetServiceResponse(
        int visitDays,
        int diaryCount,
        int diaryComboCount,
        int badgeCount
) {

    public static MemberPerformanceGetServiceResponse of(Member member) {
        return MemberPerformanceGetServiceResponse.builder()
                .visitDays(member.getVisitCount())
                .diaryCount(member.getDiaries().size())
                .diaryComboCount(member.getDiaryComboInfo().getDiaryComboCount())
                .badgeCount(member.getBadges().size())
                .build();
    }
}
