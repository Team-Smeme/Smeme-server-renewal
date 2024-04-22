package com.smeem.api.badge.api.v3;


import com.smeem.api.badge.api.dto.response.BadgeListResponseV3;
import com.smeem.api.badge.service.BadgeService;
import com.smeem.api.badge.service.dto.request.BadgeListServiceRequestV3;
import com.smeem.api.common.SuccessResponse;
import com.smeem.api.support.ApiResponseGenerator;
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
@RequestMapping("/api/v3/members/badges")
public class BadgeV3Controller implements BadgeV3Api {

    private final BadgeService badgeService;

        @GetMapping
        public ResponseEntity<SuccessResponse<BadgeListResponseV3>> getBadges(Principal principal) {
            val memberId = PrincipalConverter.getMemberId(principal);
            val response = BadgeListResponseV3.from(badgeService.getBadgesV3(BadgeListServiceRequestV3.of(memberId)));
            return ApiResponseGenerator.success(SUCCESS_GET_BADGES, response);
    }

}
