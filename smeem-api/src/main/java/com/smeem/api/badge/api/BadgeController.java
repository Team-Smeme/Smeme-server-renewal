package com.smeem.api.badge.api;

import com.smeem.api.badge.api.dto.response.BadgeGetResponse;
import com.smeem.api.badge.api.dto.response.BadgeListResponse;
import com.smeem.api.badge.service.BadgeService;
import com.smeem.api.support.ApiResponseGenerator;
import com.smeem.api.common.SuccessResponse;
import com.smeem.api.support.PrincipalConverter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.smeem.common.code.success.BadgeSuccessCode.SUCCESS_GET_BADGE;
import static com.smeem.common.code.success.BadgeSuccessCode.SUCCESS_GET_BADGES;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/members/badges")
public class BadgeController implements BadgeApi {

    private final BadgeService badgeService;

    @Override
    @GetMapping("/{badgeId}")
    public ResponseEntity<SuccessResponse<BadgeGetResponse>> getBadgeById(
            Principal principal,
            @PathVariable Long badgeId
    ) {
        val memberId = PrincipalConverter.getMemberId(principal);
        val response = BadgeGetResponse.from(badgeService.getBadge(badgeId, memberId));
        return ApiResponseGenerator.success(SUCCESS_GET_BADGE, response);
    }

    @Override
    @GetMapping
    public ResponseEntity<SuccessResponse<BadgeListResponse>> getBadges(Principal principal) {
        val memberId = PrincipalConverter.getMemberId(principal);
        val response = BadgeListResponse.from(badgeService.getBadges(memberId));
        return ApiResponseGenerator.success(SUCCESS_GET_BADGES, response);
    }

}
