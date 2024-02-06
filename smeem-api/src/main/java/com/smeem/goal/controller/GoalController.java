package com.smeem.goal.controller;

import static com.smeme.server.util.ApiResponse.success;
import static com.smeme.server.util.message.ResponseMessage.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smeme.server.dto.goal.GoalResponseDTO;
import com.smeme.server.dto.goal.GoalsResponseDTO;
import com.smeme.server.model.goal.GoalType;
import com.smeme.server.service.GoalService;
import com.smeme.server.util.ApiResponse;

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
