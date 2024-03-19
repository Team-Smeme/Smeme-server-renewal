package com.smeem.api.fixture.goal;


import com.smeem.api.goal.service.dto.response.GoalGetServiceResponse;
import com.smeem.domain.goal.model.Goal;
import com.smeem.domain.goal.model.GoalType;

import static com.smeem.domain.goal.model.GoalType.DEVELOP;

public class GoalFixture {

    private static final GoalType GOAL_TYPE = DEVELOP;

    private static final String GOAL_WAY = "주 5회 이상 오늘 하루를 돌아보는 일기 작성하기";
    private static final String GOAL_DETAIL = "사전 없이 일기 완성\nsmeem 연속 일기 배지 획득";
    public static Goal createGoal() {
        return Goal.builder()
                .type(GOAL_TYPE)
                .way(GOAL_WAY)
                .detail(GOAL_DETAIL)
                .build();
    }

    public static GoalGetServiceResponse createGoalResponseDTO() {
        return GoalGetServiceResponse.of(createGoal());
    }
}
