package com.smeme.server.controller;

import com.smeme.server.dto.badge.BadgeListResponseDTO;
import com.smeme.server.service.BadgeService;
import com.smeme.server.util.ApiResponse;
import com.smeme.server.util.Util;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.smeme.server.util.message.ResponseMessage.SUCCESS_GET_BADGES;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/members/badges")
@Tag(name = "Badge", description = "뱃지 관련 API")
@SecurityRequirement(name = "Authorization")
public class BadgeController {

    private final BadgeService badgeService;

    @Operation(summary = "사용자 뱃지 조회", description = "사용자의 뱃지를 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "뱃지 조회 성공",
                    content = @Content(schema = @Schema(implementation = BadgeListResponseDTO.class)))})
    @GetMapping
    public ResponseEntity<ApiResponse> getBadgeList(Principal principal) {
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_GET_BADGES.getMessage(), badgeService.getBadgeList(Util.getMemberId(principal))));
    }

}
