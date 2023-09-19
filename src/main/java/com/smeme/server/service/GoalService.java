package com.smeme.server.service;

import static com.smeme.server.util.message.ErrorMessage.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smeme.server.dto.goal.GoalResponseDTO;
import com.smeme.server.dto.goal.GoalsResponseDTO;
import com.smeme.server.model.goal.Goal;
import com.smeme.server.model.goal.GoalType;
import com.smeme.server.repository.goal.GoalRepository;

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
                .orElseThrow(() -> new EntityNotFoundException(EMPTY_GOAL.getMessage()));
        return new GoalResponseDTO(goalType.getDescription(), goal.getWay(), goal.getDetail());
    }
}
