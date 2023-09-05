package com.smeme.server.repository.goal;

import com.smeme.server.model.goal.Goal;
import com.smeme.server.model.goal.GoalType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    Optional<Goal> findOneByType(GoalType type);
}
