package com.smeem.support.fixture;

import com.smeem.domain.goal.model.Goal;
import com.smeem.domain.goal.model.GoalType;

public class GoalFixture {

    private GoalType type;

    private String way = "test-goal-way";
    private String detail = "test-goal-detail";

    public static GoalFixture goal() {
        return new GoalFixture();
    }

    public GoalFixture type(GoalType type) {
        this.type = type;
        return this;
    }

    public Goal build() {
        return Goal.builder()
                .type(type)
                .way(way)
                .detail(detail)
                .build();
    }

}
