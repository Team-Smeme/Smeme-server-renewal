package com.smeem.api.goal.service;

import java.util.List;

import com.smeem.api.goal.dto.request.GoalGetServiceRequest;
import com.smeem.api.goal.dto.response.GoalGetServiceResponse;
import com.smeem.api.goal.dto.response.GoalListGetServiceResponse;
import com.smeem.common.exception.GoalException;
import com.smeem.domain.goal.model.Goal;
import com.smeem.domain.goal.model.GoalType;
import com.smeem.domain.goal.repository.GoalRepository;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import static com.smeem.common.code.failure.GoalFailureCode.EMPTY_GOAL;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoalService {

    private final GoalRepository goalRepository;

    public GoalListGetServiceResponse getAllGoals() {
        val goalTypes = List.of(GoalType.values());
        return GoalListGetServiceResponse.of(goalTypes);
    }

    public GoalGetServiceResponse getByType(GoalGetServiceRequest request) {
        val goal = findGoal(request.goalType());
        return GoalGetServiceResponse.of(goal);
    }

    private Goal findGoal(GoalType type) {
        return goalRepository.findOneByType(type)
                .orElseThrow(() -> new GoalException(EMPTY_GOAL));
    }
}
