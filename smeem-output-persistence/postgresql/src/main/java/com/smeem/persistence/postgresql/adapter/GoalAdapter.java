package com.smeem.persistence.postgresql.adapter;

import com.smeem.application.domain.goal.Goal;
import com.smeem.application.domain.goal.GoalType;
import com.smeem.application.port.output.persistence.GoalPort;
import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import com.smeem.persistence.postgresql.persistence.entity.GoalEntity;
import com.smeem.persistence.postgresql.persistence.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GoalAdapter implements GoalPort {
    private final GoalRepository goalRepository;

    @Override
    public List<Goal> findAll() {
        return goalRepository.findAll().stream().map(GoalEntity::toDomain).toList();
    }

    @Override
    public Goal findByGoalType(GoalType goalType) {
        val foundGoal = goalRepository.findByGoalType(goalType)
                .orElseThrow(() -> new SmeemException(
                        ExceptionCode.NOT_FOUND,
                        "(서버 개발자에게 문의: GoalType: " + goalType + ")"));
        return foundGoal.toDomain();
    }

    @Override
    public Goal findById(long goalId) {
        val foundGoal = find(goalId);
        return foundGoal.toDomain();
    }

    private GoalEntity find(long goalId) {
        return goalRepository.findById(goalId)
                .orElseThrow(() -> new SmeemException(ExceptionCode.NOT_FOUND, "Goal ID: " + goalId));
    }
}
