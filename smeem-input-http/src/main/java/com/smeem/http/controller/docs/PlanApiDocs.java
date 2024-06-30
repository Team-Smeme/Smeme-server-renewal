package com.smeem.http.controller.docs;

import com.smeem.http.controller.dto.response.SmeemResponse;
import com.smeem.http.controller.dto.response.plan.RetrievePlansResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "PlanApi", description = "학습계획(Plan) 관련 Api 입니다.")
public interface PlanApiDocs {

    @Operation(summary = "학습계획 목록 조회 api", description = "전체 학습계획을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK success",
                    content = @Content(schema = @Schema(implementation = RetrievePlansResponse.class)))
    })
    SmeemResponse<RetrievePlansResponse> retrievePlans();
}
