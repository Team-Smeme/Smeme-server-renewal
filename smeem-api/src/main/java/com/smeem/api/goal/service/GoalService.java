package com.smeem.api.goal.service;


import java.util.List;

import com.smeem.api.goal.controller.dto.response.GoalResponseDTO;
import com.smeem.api.goal.controller.dto.response.GoalsResponseDTO;
import com.smeem.common.exception.GoalException;
import com.smeem.domain.goal.model.Goal;
import com.smeem.domain.goal.model.GoalType;
import com.smeem.domain.goal.repository.GoalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import static com.smeem.common.code.failure.GoalFailureCode.EMPTY_GOAL;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoalService {

    private final GoalRepository goalRepository;

    public GoalsResponseDTO getAll() {
        List<GoalType> goalTypes = List.of(GoalType.values());
        return GoalsResponseDTO.of(goalTypes);
    }

    public GoalResponseDTO getByType(GoalType goalType) {
        Goal goal = goalRepository.findOneByType(goalType)
                .orElseThrow(() -> new GoalException(EMPTY_GOAL));
        return new GoalResponseDTO(goal.getType().getDescription(), goal.getWay(), goal.getDetail());
    }
}
