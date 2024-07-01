package com.smeem.http.controller;

import com.smeem.application.domain.generic.SmeemMessage;
import com.smeem.application.port.input.BadgeUseCase;
import com.smeem.application.port.input.dto.response.SmeemResponse;
import com.smeem.application.port.input.dto.response.badge.RetrieveMemberBadgesResponse;
import com.smeem.http.controller.docs.MemberBadgeApiV3Docs;
import com.smeem.http.util.SmeemConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/members/badges")
public class MemberBadgeApiV3 implements MemberBadgeApiV3Docs {
    private final BadgeUseCase badgeUseCase;
    private final SmeemConverter smeemConverter;

    @Override
    public SmeemResponse<RetrieveMemberBadgesResponse> retrieveBadges(Principal principal) {
        return SmeemResponse.of(
                badgeUseCase.retrieveMemberBadges(smeemConverter.toMemberId(principal)),
                SmeemMessage.RETRIEVE_BADGE);
    }
}
