package com.smeem.domain.goal.adapter;

import com.smeem.domain.goal.exception.GoalException;
import com.smeem.domain.goal.model.Goal;
import com.smeem.domain.goal.model.GoalType;
import com.smeem.domain.goal.repository.GoalRepository;
import com.smeem.domain.support.RepositoryAdapter;
import lombok.RequiredArgsConstructor;

import static com.smeem.common.code.failure.GoalFailureCode.EMPTY_GOAL;

@RepositoryAdapter
@RequiredArgsConstructor
public class GoalFinder {

    private final GoalRepository goalRepository;

    public Goal findByType(final GoalType type) {
        return goalRepository.findOneByType(type)
                .orElseThrow(() -> new GoalException(EMPTY_GOAL));
    }
}
