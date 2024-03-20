package com.smeem.api.badge.api;

import com.smeem.api.badge.api.dto.response.BadgeListResponse;
import com.smeem.api.badge.service.BadgeService;
import com.smeem.api.common.ApiResponseUtil;
import com.smeem.api.common.dto.SuccessResponse;
import com.smeem.common.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.smeem.common.code.success.BadgeSuccessCode.SUCCESS_GET_BADGES;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/members/badges")
public class BadgeController implements BadgeApi {

    private final BadgeService badgeService;

    @Override
    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getBadges(Principal principal) {
        val response = BadgeListResponse.from(badgeService.getBadges(Util.getMemberId(principal)));
        return ApiResponseUtil.success(SUCCESS_GET_BADGES, response);
    }

}
