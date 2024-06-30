package com.smeem.http.controller;

import com.smeem.application.port.input.dto.response.SmeemResponse;
import com.smeem.application.port.input.dto.response.badge.RetrieveBadgesResponse;
import com.smeem.http.controller.docs.MemberBadgeApiDocs;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/members/badges")
public class MemberBadgeApi implements MemberBadgeApiDocs {

    @Override
    public SmeemResponse<RetrieveBadgesResponse> retrieveBadges(Principal principal) {
        return null;
    }
}
