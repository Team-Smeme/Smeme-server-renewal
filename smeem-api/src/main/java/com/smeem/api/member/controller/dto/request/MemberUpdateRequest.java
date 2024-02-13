package com.smeem.api.member.controller.dto.request;


import com.smeem.api.member.controller.dto.ValidUsername;

public record MemberUpdateRequest(
        @ValidUsername
        String username,
        Boolean termAccepted
) {
}
