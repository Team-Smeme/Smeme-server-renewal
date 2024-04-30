package com.smeem.api.member.api.dto.response;

import com.smeem.api.member.service.dto.response.MemberPlanGetServiceResponse;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record MemberPlanGetResponse(
        String plan,
        String goal,
        int clearedCount,
        int clearCount
) {

    public static MemberPlanGetResponse from(MemberPlanGetServiceResponse response) {
        return MemberPlanGetResponse.builder()
                .plan(response.plan().content())
                .goal(response.goal().goalType().getDescription())
                .clearedCount(response.plan().clearedCount())
                .clearCount(response.plan().clearCount())
                .build();
    }
}
