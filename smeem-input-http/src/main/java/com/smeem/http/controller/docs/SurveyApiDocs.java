package com.smeem.http.controller.docs;

import com.smeem.application.port.input.dto.request.survey.CoachingSurveyRequest;
import com.smeem.http.controller.dto.SmeemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.security.Principal;

@Tag(name = "SurveyApi", description = "설문조사 관련 Api 입니다.")
public interface SurveyApiDocs {
    @Operation(summary = "코칭 만족도 조사 api", description = "코칭 만족도 조사를 결과를 전송합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK success")
    })
    SmeemResponse<?> saveCoachingSurveyResult(
            @Parameter(hidden = true) Principal principal,
            @RequestBody(
                    description = "만족도 조사 Request Body",
                    content = @Content(schema = @Schema(implementation = CoachingSurveyRequest.class))
            ) CoachingSurveyRequest request);
}