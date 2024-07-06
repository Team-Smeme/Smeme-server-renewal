package com.smeem.application.port.input;

import com.smeem.application.domain.goal.GoalType;
import com.smeem.application.port.input.dto.response.goal.RetrieveGoalResponse;
import com.smeem.application.port.input.dto.response.goal.RetrieveGoalsResponse;

public interface GoalUseCase {
    RetrieveGoalsResponse retrieveGoals();
    RetrieveGoalResponse retrieveGoal(GoalType goalType);
}
