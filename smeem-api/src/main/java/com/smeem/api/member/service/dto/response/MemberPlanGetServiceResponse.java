package com.smeem.api.member.service.dto.response;

import com.smeem.domain.goal.model.GoalType;
import com.smeem.domain.member.model.Member;
import lombok.Builder;
import lombok.val;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record MemberPlanGetServiceResponse(
        MemberGoalServiceResponse goal,
        MemberPlanServiceResponse plan
) {

    public static MemberPlanGetServiceResponse of(Member member) {
        return MemberPlanGetServiceResponse.builder()
                .goal(MemberGoalServiceResponse.of(member))
                .plan(MemberPlanServiceResponse.of(member))
                .build();
    }

    @Builder(access = PRIVATE)
    public record MemberPlanServiceResponse(
            String content,
            int clearCount,
            int clearedCount
    ) {

        private static MemberPlanServiceResponse of(Member member) {
            val plan = member.getPlan();
            return MemberPlanServiceResponse.builder()
                    .content(plan.getContent())
                    .clearCount(plan.getClearCount())
                    .clearedCount(member.getDiaryCountInWeek())
                    .build();
        }
    }

    @Builder(access = PRIVATE)
    public record MemberGoalServiceResponse(
            GoalType goalType
    ) {

        private static MemberGoalServiceResponse of(Member member) {
            val goalType = member.getGoal();
            return MemberGoalServiceResponse.builder()
                    .goalType(goalType)
                    .build();
        }
    }
}
