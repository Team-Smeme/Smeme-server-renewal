package com.smeme.server.controller;

import com.smeme.server.service.BadgeService;
import com.smeme.server.util.ApiResponse;
import com.smeme.server.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.smeme.server.util.message.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/members/badges")
public class BadgeController {

    private final BadgeService badgeService;

    @GetMapping
    public ResponseEntity<ApiResponse> getBadgeList(Principal principal) {
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_GET_BADGES.getMessage(), badgeService.getBadgeList(Util.getMemberId(principal))));
    }

}
