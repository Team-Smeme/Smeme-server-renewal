package com.smeem.http.controller.docs;

import com.smeem.application.port.input.dto.response.SmeemResponse;
import com.smeem.application.port.input.dto.response.goal.RetrieveGoalResponse;
import com.smeem.application.port.input.dto.response.goal.RetrieveGoalsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "GoalApi", description = "학습목표(Goal) 관련 Api 입니다.")
public interface GoalApiDocs {

    @Operation(summary = "학습목표 목록 조회 api", description = "학습목표를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK success",
                    content = @Content(schema = @Schema(implementation = RetrieveGoalsResponse.class)))
    })
    SmeemResponse<RetrieveGoalsResponse> retrieveGoals();

    @Operation(summary = "학습목표 상세 조회 api", description = "학습목표를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK success",
                    content = @Content(schema = @Schema(implementation = RetrieveGoalResponse.class)))
    })
    SmeemResponse<RetrieveGoalResponse> retrieveGoal(
            @Parameter(
                    name = "type",
                    description = "학습목표 유형",
                    required = true,
                    in = ParameterIn.PATH
            ) String type //TODO: GoalType
    );
}
