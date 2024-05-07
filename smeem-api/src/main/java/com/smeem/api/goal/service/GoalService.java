package com.smeem.api.goal.service;

import java.util.List;
import com.smeem.api.goal.service.dto.request.GoalGetServiceRequest;
import com.smeem.api.goal.service.dto.response.GoalGetServiceResponse;
import com.smeem.api.goal.service.dto.response.GoalListGetServiceResponse;
import com.smeem.domain.goal.adapter.GoalFinder;
import com.smeem.domain.goal.model.GoalType;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoalService {

    private final GoalFinder goalFinder;

    public GoalListGetServiceResponse getAllGoals() {
        val goalTypes = List.of(GoalType.values());
        return GoalListGetServiceResponse.of(goalTypes);
    }

    public GoalGetServiceResponse getByType(final GoalGetServiceRequest request) {
        val goal = goalFinder.findByType(request.goalType());
        return GoalGetServiceResponse.of(goal);
    }
}
