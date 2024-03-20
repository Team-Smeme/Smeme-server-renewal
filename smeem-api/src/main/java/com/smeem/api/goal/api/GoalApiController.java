package com.smeem.api.goal.api;

import com.smeem.api.common.ApiResponseUtil;
import com.smeem.api.common.dto.SuccessResponse;

import com.smeem.api.goal.api.dto.response.GoalGetResponse;
import com.smeem.api.goal.api.dto.response.GoalListGetResponse;
import com.smeem.api.goal.service.dto.request.GoalGetServiceRequest;
import com.smeem.api.goal.service.GoalService;
import com.smeem.domain.goal.model.GoalType;
import lombok.val;
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
public class GoalApiController implements GoalApi {

    private final GoalService goalService;

    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getAllGoals() {
        val response = GoalListGetResponse.from(goalService.getAllGoals());
        return ApiResponseUtil.success(SUCCESS_GET_GOALS, response);
    }

    @GetMapping("/{type}")
    public ResponseEntity<SuccessResponse<?>> getGoalByType(@PathVariable GoalType type) {
        val response = GoalGetResponse.from(goalService.getByType(GoalGetServiceRequest.of(type)));
        return ApiResponseUtil.success(SUCCESS_GET_GOAL, response);
    }
}
