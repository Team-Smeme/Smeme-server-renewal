package com.smeem.api.test.api;

import com.smeem.api.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface TestApi {

    @Operation(
            summary = "서버 연결 테스트",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
            }
    )
    ResponseEntity<BaseResponse<?>> test();

    @Operation(
            summary = "푸시알림 테스트",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰"),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
            }
    )
    ResponseEntity<BaseResponse<?>> alarmTest(Principal principal);
}
