package com.smeem.api.goal.controller;


import com.smeem.api.common.ApiResponse;
import com.smeem.api.goal.controller.dto.response.GoalResponseDTO;
import com.smeem.api.goal.controller.dto.response.GoalsResponseDTO;
import com.smeem.api.goal.service.GoalService;
import com.smeem.common.code.ResponseMessage;
import com.smeem.domain.goal.model.GoalType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/goals")
public class GoalController {

    private final GoalService goalService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
        GoalsResponseDTO response = goalService.getAll();
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_GOALS.getMessage(), response));
    }

    @GetMapping("/{type}")
    public ResponseEntity<ApiResponse> getByType(@PathVariable GoalType type) {
        GoalResponseDTO response = goalService.getByType(type);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_GOAL.getMessage(), response));
    }
}
