package com.smeme.server.controller;

import static com.smeme.server.util.message.ResponseMessage.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smeme.server.dto.goal.GoalsResponseDTO;
import com.smeme.server.service.GoalService;
import com.smeme.server.util.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/goals")
public class GoalController {

	private final GoalService goalService;

	@GetMapping
	public ResponseEntity<ApiResponse> getAllGoals() {
		GoalsResponseDTO response = goalService.getAllGoals();
		return ResponseEntity.ok(ApiResponse.success(SUCCESS_GET_GOALS.getMessage(), response));
	}
}
