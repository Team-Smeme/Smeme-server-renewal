package com.smeem.http.controller.docs;

import com.smeem.application.port.input.dto.request.plan.UpdateMemberPlanRequest;
import com.smeem.http.controller.dto.SmeemResponse;
import com.smeem.application.port.input.dto.response.plan.RetrieveMemberPlanResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.security.Principal;

@Tag(name = "MemberPlanApi", description = "회원의 학습계획 관련 Api 입니다.")
public interface MemberPlanApiDocs {

    @Operation(summary = "학습계획 수정 api", description = "회원의 학습계획을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK success")
    })
    SmeemResponse<?> updatePlan(
            @Parameter(hidden = true) Principal principal,
            @RequestBody(
                    description = "학습계획 수정 Request Body",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UpdateMemberPlanRequest.class))
            ) UpdateMemberPlanRequest request
    );

    @Operation(summary = "학습계획 api", description = "회원의 학습계획을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK success",
                    content = @Content(schema = @Schema(implementation = RetrieveMemberPlanResponse.class)))
    })
    SmeemResponse<RetrieveMemberPlanResponse> retrievePlan(@Parameter(hidden = true) Principal principal);
}
