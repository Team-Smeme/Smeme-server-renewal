package com.smeem.api.goal.api;

import com.smeem.api.common.BaseResponse;
import com.smeem.domain.goal.model.GoalType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface GoalApi {

    @Operation(
            summary = "목표 전체 목록 조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
            }
    )
    ResponseEntity<BaseResponse<?>> getAllGoals();

    @Operation(
            summary = "목표 상세 조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
            }
    )
    ResponseEntity<BaseResponse<?>> getGoalByType(@PathVariable GoalType type);
}
