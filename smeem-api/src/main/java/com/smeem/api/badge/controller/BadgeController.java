package com.smeem.api.badge.controller;

import com.smeem.api.badge.controller.dto.response.BadgeListResponseDTO;
import com.smeem.api.badge.service.BadgeService;
import com.smeem.api.common.ApiResponseUtil;
import com.smeem.api.common.BaseResponse;
import com.smeem.common.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.smeem.common.code.success.BadgeSuccessCode.SUCCESS_GET_BADGES;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/members/badges")
public class BadgeController {

    private final BadgeService badgeService;

    @GetMapping
    public ResponseEntity<BaseResponse<?>> getBadgeList(Principal principal) {
        BadgeListResponseDTO response = badgeService.getBadgeList(Util.getMemberId(principal));
        return ApiResponseUtil.success(SUCCESS_GET_BADGES, response);
    }

}
