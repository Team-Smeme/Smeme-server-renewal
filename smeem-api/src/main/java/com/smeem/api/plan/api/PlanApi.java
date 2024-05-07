package com.smeem.api.plan.api;

import com.smeem.api.common.FailureResponse;
import com.smeem.api.common.SuccessResponse;
import com.smeem.api.plan.api.dto.response.PlansAllGetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "[Plan] 플랜 관련 API (V2)")
public interface PlanApi {

    @Operation(summary = "플랜 전체 목록 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "플랜 목록 조회 성공"),
            @ApiResponse(
                    responseCode = "4xx",
                    description = "유효하지 않은 요청",
                    content = @Content(schema = @Schema(implementation = FailureResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 오류",
                    content = @Content(schema = @Schema(implementation = FailureResponse.class))
            )
    })
    ResponseEntity<SuccessResponse<PlansAllGetResponse>> getAllPlans();
}
