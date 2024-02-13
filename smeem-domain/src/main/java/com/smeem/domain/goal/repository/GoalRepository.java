package com.smeem.domain.goal.repository;

import com.smeem.domain.goal.model.Goal;
import com.smeem.domain.goal.model.GoalType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    Optional<Goal> findOneByType(GoalType type);
}
