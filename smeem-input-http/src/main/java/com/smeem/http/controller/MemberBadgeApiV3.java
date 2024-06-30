package com.smeem.http.controller;

import com.smeem.application.port.input.dto.response.SmeemResponse;
import com.smeem.application.port.input.dto.response.badge.RetrieveMemberBadgesResponse;
import com.smeem.http.controller.docs.MemberBadgeApiV3Docs;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/members/badges")
public class MemberBadgeApiV3 implements MemberBadgeApiV3Docs {

    @Override
    public SmeemResponse<RetrieveMemberBadgesResponse> retrieveBadges(Principal principal) {
        return null;
    }
}
