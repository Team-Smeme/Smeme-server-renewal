package com.smeem.api.badge.controller;

import com.smeem.api.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.security.Principal;


@Tag(name = "[Badge] 뱃지 관련 API (V2)")
public interface BadgeApi {

    @Operation(summary = "뱃지 목록 조회 API")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "뱃지 목록 조회 성공"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    ResponseEntity<BaseResponse<?>> getBadges(Principal principal);

}
