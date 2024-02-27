package com.smeem.api.goal.service.dto.request;

import com.smeem.domain.goal.model.GoalType;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record GoalGetServiceRequest(
        GoalType goalType
) {

    public static GoalGetServiceRequest of(GoalType goalType) {
        return GoalGetServiceRequest.builder()
                .goalType(goalType)
                .build();
    }
}
