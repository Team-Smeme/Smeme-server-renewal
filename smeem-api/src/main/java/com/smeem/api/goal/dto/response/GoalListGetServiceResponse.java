package com.smeem.api.goal.dto.response;

import com.smeem.domain.goal.model.GoalType;
import lombok.Builder;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record GoalListGetServiceResponse(
        List<GoalResponse> goals
) {

    public static GoalListGetServiceResponse of(List<GoalType> goalTypes) {
        return GoalListGetServiceResponse.builder()
                .goals(goalTypes.stream().map(GoalResponse::of).toList())
                .build();
    }

    @Builder(access = PRIVATE)
    public record GoalResponse(
            String goalType,
            String name
    ) {

        public static GoalResponse of(GoalType goalType) {
            return GoalResponse.builder()
                    .goalType(goalType.name())
                    .name(goalType.getDescription())
                    .build();
        }
    }
}
