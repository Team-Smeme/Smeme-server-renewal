package com.smeme.server.controller;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Goal", description = "학습 목표 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/goals")
public class GoalController {

    private final GoalService goalService;

    @Operation(description = "전체 학습 목표 조회")
    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
        GoalsResponseDTO response = goalService.getAll();
        return ResponseEntity.ok(success(SUCCESS_GET_GOALS.getMessage(), response));
    }

    @Operation(description = "학습 목표 조회")
    @GetMapping("/{type}")
    public ResponseEntity<ApiResponse> getByType(@PathVariable GoalType type) {
        GoalResponseDTO response = goalService.getByType(type);
        return ResponseEntity.ok(success(SUCCESS_GET_GOAL.getMessage(), response));
    }
}
