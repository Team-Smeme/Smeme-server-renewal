package com.smeem.api.topic.api;

import com.smeem.api.common.FailureResponse;
import com.smeem.api.common.SuccessResponse;
import com.smeem.api.topic.api.dto.response.RandomTopicGetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

@Tag(name = "[Topic] 주제 관련 API (V2)")
public interface TopicApi {

    @Operation(summary = "랜덤 주제 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(
                    responseCode = "4xx",
                    description = "유효하지 않은 요청",
                    content = @Content(schema = @Schema(implementation = FailureResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "유효하지 않은 토큰",
                    content = @Content(schema = @Schema(implementation = FailureResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content(schema = @Schema(implementation = FailureResponse.class))
            )
    })
    ResponseEntity<SuccessResponse<RandomTopicGetResponse>> getTopicByRandom();
}
