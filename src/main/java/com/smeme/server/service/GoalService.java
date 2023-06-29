package com.smeme.server.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smeme.server.dto.goal.GoalsResponseDTO;
import com.smeme.server.model.goal.GoalType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoalService {

	public GoalsResponseDTO getAllGoals() {
		List<GoalType> goalTypes = List.of(GoalType.values());
		return GoalsResponseDTO.of(goalTypes);
	}
}
