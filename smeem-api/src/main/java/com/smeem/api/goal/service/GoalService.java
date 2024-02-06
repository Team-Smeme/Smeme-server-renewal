package com.smeem.api.goal.service;


import java.util.List;

import com.smeem.api.goal.controller.dto.response.GoalResponseDTO;
import com.smeem.api.goal.controller.dto.response.GoalsResponseDTO;
import com.smeem.common.code.ErrorMessage;
import com.smeem.domain.goal.model.Goal;
import com.smeem.domain.goal.model.GoalType;
import com.smeem.domain.goal.repository.GoalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

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
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.EMPTY_GOAL.getMessage()));
        return new GoalResponseDTO(goal.getType().getDescription(), goal.getWay(), goal.getDetail());
    }
}
