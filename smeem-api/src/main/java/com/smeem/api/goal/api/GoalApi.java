package com.smeem.api.goal.api;

import com.smeem.api.common.FailureResponse;
import com.smeem.api.common.SuccessResponse;
import com.smeem.api.goal.api.dto.response.GoalGetResponse;
import com.smeem.api.goal.api.dto.response.GoalListGetResponse;
import com.smeem.domain.goal.model.GoalType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "[Goal] 목표 관련 API (V2)")
public interface GoalApi {

    @Operation(summary = "목표 전체 목록 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(
                    responseCode = "4xx",
                    description = "유효하지 않은 요청",
                    content = @Content(schema = @Schema(implementation = FailureResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content(schema = @Schema(implementation = FailureResponse.class))
            )
    })
    ResponseEntity<SuccessResponse<GoalListGetResponse>> getAllGoals();

    @Operation(summary = "목표 상세 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(
                    responseCode = "4xx",
                    description = "유효하지 않은 요청",
                    content = @Content(schema = @Schema(implementation = FailureResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content(schema = @Schema(implementation = FailureResponse.class))
            )
    })
    ResponseEntity<SuccessResponse<GoalGetResponse>> getGoalByType(@PathVariable GoalType type);
}
