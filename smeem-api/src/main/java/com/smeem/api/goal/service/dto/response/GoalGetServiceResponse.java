package com.smeem.api.goal.service.dto.response;

import com.smeem.domain.goal.model.Goal;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record GoalGetServiceResponse(
        String goalType,
        String way,
        String detail
) {

    public static GoalGetServiceResponse of(Goal goal) {
        return GoalGetServiceResponse.builder()
                .goalType(goal.getType().name())
                .way(goal.getWay())
                .detail(goal.getDetail())
                .build();
    }
}
