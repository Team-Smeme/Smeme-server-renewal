package com.smeem.api.goal.controller;


import com.smeem.api.common.ApiResponseUtil;
import com.smeem.api.common.BaseResponse;
import com.smeem.api.goal.controller.dto.response.GoalResponseDTO;
import com.smeem.api.goal.controller.dto.response.GoalsResponseDTO;
import com.smeem.api.goal.service.GoalService;
import com.smeem.domain.goal.model.GoalType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import static com.smeem.common.code.success.GoalSuccessCode.SUCCESS_GET_GOAL;
import static com.smeem.common.code.success.GoalSuccessCode.SUCCESS_GET_GOALS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/goals")
public class GoalController {

    private final GoalService goalService;

    @GetMapping
    public ResponseEntity<BaseResponse<?>> getAll() {
        GoalsResponseDTO response = goalService.getAll();
        return ApiResponseUtil.success(SUCCESS_GET_GOALS, response);
    }

    @GetMapping("/{type}")
    public ResponseEntity<BaseResponse<?>> getByType(@PathVariable GoalType type) {
        GoalResponseDTO response = goalService.getByType(type);
        return ApiResponseUtil.success(SUCCESS_GET_GOAL, response);
    }
}
