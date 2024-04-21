package com.smeem.api.badge.api.v3;

import com.smeem.api.badge.service.dto.response.v3.BadgeListServiceResponseV3;
import com.smeem.api.common.FailureResponse;
import com.smeem.api.common.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Tag(name = "[Badge] 뱃지 관련 API (V3)")
public interface BadgeV3Api {

    @Operation(summary = "뱃지 목록 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "뱃지 리스트 조회 성공"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰입니다", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류", content = @Content(schema = @Schema(implementation = FailureResponse.class)))
    })
    @GetMapping
    ResponseEntity<SuccessResponse<BadgeListServiceResponseV3>> getBadges(@Parameter(hidden = true) Principal principal);

}
