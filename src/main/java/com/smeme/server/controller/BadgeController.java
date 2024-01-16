package com.smeme.server.controller;

import com.smeme.server.dto.badge.BadgeListResponseDTO;
import com.smeme.server.service.BadgeService;
import com.smeme.server.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.smeme.server.util.ApiResponse.success;
import static com.smeme.server.util.Util.getMemberId;
import static com.smeme.server.util.message.ResponseMessage.SUCCESS_GET_BADGES;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/members/badges")
public class BadgeController {

    private final BadgeService badgeService;

    @GetMapping
    public ResponseEntity<ApiResponse> getBadgeList(Principal principal) {
        BadgeListResponseDTO response = badgeService.getBadgeList(getMemberId(principal));
        return ResponseEntity.ok(success(SUCCESS_GET_BADGES.getMessage(), response));
    }

}
