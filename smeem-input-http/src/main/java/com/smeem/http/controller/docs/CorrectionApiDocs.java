package com.smeem.http.controller.docs;

import com.smeem.application.port.input.dto.response.diary.CorrectionsResponse;
import com.smeem.http.controller.dto.SmeemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.security.Principal;

@Tag(name = "[Correction] 코칭", description = "코칭 기능 관련 API")
public interface CorrectionApiDocs {

    @Operation(summary = "일기 코칭 api", description = "AI 코칭 실행 및 결과 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK success",
                    content = @Content(schema = @Schema(implementation = CorrectionsResponse.class)))
    })
    SmeemResponse<CorrectionsResponse> correctDiary(
            @Parameter(hidden = true) Principal principal,
            @Parameter(
                    name = "diaryId",
                    description = "코칭할 일기 id",
                    required = true,
                    in = ParameterIn.PATH
            ) long diaryId
    );
}
