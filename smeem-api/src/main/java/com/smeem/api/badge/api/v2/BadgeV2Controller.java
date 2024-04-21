package com.smeem.api.badge.api.v2;

import com.smeem.api.badge.api.dto.response.BadgeListResponse;
import com.smeem.api.badge.service.BadgeService;
import com.smeem.api.support.ApiResponseGenerator;
import com.smeem.api.common.SuccessResponse;
import com.smeem.api.support.PrincipalConverter;
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
public class BadgeV2Controller implements BadgeV2Api {

    private final BadgeService badgeService;

    @Override
    @GetMapping
    public ResponseEntity<SuccessResponse<BadgeListResponse>> getBadges(Principal principal) {
        val memberId = PrincipalConverter.getMemberId(principal);
        val response = BadgeListResponse.from(badgeService.getBadges(memberId));
        return ApiResponseGenerator.success(SUCCESS_GET_BADGES, response);
    }

}
