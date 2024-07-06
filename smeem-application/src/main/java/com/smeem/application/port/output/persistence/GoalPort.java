package com.smeem.application.port.output.persistence;

import com.smeem.application.domain.goal.Goal;
import com.smeem.application.domain.goal.GoalType;

import java.util.List;

public interface GoalPort {
    List<Goal> findAll();
    Goal findByGoalType(GoalType goalType);
    Goal findById(long goalId);
}
