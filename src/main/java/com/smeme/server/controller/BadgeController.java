package com.smeme.server.controller;

import com.smeme.server.dto.badge.BadgeListResponseDTO;
import com.smeme.server.service.BadgeService;
import com.smeme.server.util.ApiResponse;
import com.smeme.server.util.Util;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "Authorization")
public class BadgeController {

    private final BadgeService badgeService;

    @GetMapping
    public ResponseEntity<ApiResponse> getBadgeList(Principal principal) {
        BadgeListResponseDTO response = badgeService.getBadgeList(Util.getMemberId(principal));
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_GET_BADGES.getMessage(), response));
    }

}
