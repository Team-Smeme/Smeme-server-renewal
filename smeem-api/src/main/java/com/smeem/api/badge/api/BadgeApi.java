package com.smeem.api.badge.api;

import com.smeem.api.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;


@Tag(name = "[Badge] 뱃지 관련 API (V2)")
public interface BadgeApi {

    @Operation(summary = "뱃지 목록 조회 API")
    @Parameter(name = "Authorization", description = "Bearer ${Smeme Access Token}", in = HEADER, required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "뱃지 리스트 조회 성공"),
            @ApiResponse(responseCode = "4011", description = "유효하지 않은 토큰입니다"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    ResponseEntity<BaseResponse<?>> getBadges(Principal principal);

}