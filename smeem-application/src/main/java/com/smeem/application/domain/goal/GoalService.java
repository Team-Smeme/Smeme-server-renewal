package com.smeem.application.domain.goal;

import com.smeem.application.port.input.GoalUseCase;
import com.smeem.application.port.input.dto.response.goal.RetrieveGoalResponse;
import com.smeem.application.port.input.dto.response.goal.RetrieveGoalsResponse;
import com.smeem.application.port.output.persistence.GoalPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GoalService implements GoalUseCase {
    private final GoalPort goalPort;

    public RetrieveGoalsResponse retrieveGoals() {
        return RetrieveGoalsResponse.of(goalPort.findAll());
    }

    public RetrieveGoalResponse retrieveGoal(GoalType goalType) {
        return RetrieveGoalResponse.of(goalPort.findByGoalType(goalType));
    }
}
