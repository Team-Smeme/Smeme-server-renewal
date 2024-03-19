package com.smeem.api.test.api;

import com.smeem.api.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

@Tag(name = "[Test] 테스트 관련 API (V2)")
public interface TestApi {

    @Operation(summary = "서버 연결 테스트 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "4xx", description = "유효하지 않은 요청"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    ResponseEntity<BaseResponse<?>> test();

    @Operation(summary = "푸시알림 테스트 API")
    @Parameter(name = "Authorization", description = "Bearer ${Smeem Access Token}", in = HEADER, required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "4xx", description = "유효하지 않은 요청"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    ResponseEntity<BaseResponse<?>> alarmTest(Principal principal);
}
