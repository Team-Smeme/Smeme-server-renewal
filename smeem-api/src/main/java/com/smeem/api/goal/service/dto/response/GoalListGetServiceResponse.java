package com.smeem.api.goal.service.dto.response;

import com.smeem.domain.goal.model.GoalType;
import lombok.Builder;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record GoalListGetServiceResponse(
        List<GoalServiceResponse> goals
) {

    public static GoalListGetServiceResponse of(List<GoalType> goalTypes) {
        return GoalListGetServiceResponse.builder()
                .goals(goalTypes.stream().map(GoalServiceResponse::of).toList())
                .build();
    }

    @Builder(access = PRIVATE)
    public record GoalServiceResponse(
            String goalType,
            String goalDescription
    ) {

        public static GoalServiceResponse of(GoalType goalType) {
            return GoalServiceResponse.builder()
                    .goalType(goalType.name())
                    .goalDescription(goalType.getDescription())
                    .build();
        }
    }
}
