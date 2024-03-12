package com.smeem.api.topic.api;

import com.smeem.api.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface TopicApi {

    @Operation(
            summary = "랜덤 주제 조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
            }
    )
    ResponseEntity<BaseResponse<?>> getTopicByRandom();
}
