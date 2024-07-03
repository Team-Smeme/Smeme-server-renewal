package com.smeem.persistence.postgresql.persistence.repository;

import com.smeem.application.domain.goal.GoalType;
import com.smeem.persistence.postgresql.persistence.entity.GoalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoalRepository extends JpaRepository<GoalEntity, Long> {
    Optional<GoalEntity> findByGoalType(GoalType goalType);
}
