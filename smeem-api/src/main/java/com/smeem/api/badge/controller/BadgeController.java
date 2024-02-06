package com.smeem.api.badge.controller;

import com.smeem.api.badge.controller.dto.response.BadgeListResponseDTO;
import com.smeem.api.badge.service.BadgeService;
import com.smeem.api.common.ApiResponse;
import com.smeem.common.code.ResponseMessage;
import com.smeem.common.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/members/badges")
public class BadgeController {

    private final BadgeService badgeService;

    @GetMapping
    public ResponseEntity<ApiResponse> getBadgeList(Principal principal) {
        BadgeListResponseDTO response = badgeService.getBadgeList(Util.getMemberId(principal));
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_BADGES.getMessage(), response));
    }

}
