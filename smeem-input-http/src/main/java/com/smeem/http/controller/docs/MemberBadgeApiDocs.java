package com.smeem.http.controller.docs;

import com.smeem.application.port.input.dto.response.SmeemResponse;
import com.smeem.application.port.input.dto.response.badge.RetrieveBadgesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.security.Principal;

@Tag(name = "MemberBadgeApi", description = "회원의 배지(Badge) 관련 Api 입니다.")
public interface MemberBadgeApiDocs {

    @Operation(summary = "배지 조회 api", description = "회원의 배지 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK success",
                    content = @Content(schema = @Schema(implementation = RetrieveBadgesResponse.class)))
    })
    SmeemResponse<RetrieveBadgesResponse> retrieveBadges(@Parameter(hidden = true) Principal principal);
}
