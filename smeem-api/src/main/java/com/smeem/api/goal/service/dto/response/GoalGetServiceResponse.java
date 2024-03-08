package com.smeem.api.goal.service.dto.response;

import com.smeem.domain.goal.model.Goal;
import com.smeem.domain.goal.model.GoalType;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record GoalGetServiceResponse(
        GoalType goalType,
        String way,
        String detail
) {

    public static GoalGetServiceResponse of(Goal goal) {
        return GoalGetServiceResponse.builder()
                .goalType(goal.getType())
                .way(goal.getWay())
                .detail(goal.getDetail())
                .build();
    }
}
