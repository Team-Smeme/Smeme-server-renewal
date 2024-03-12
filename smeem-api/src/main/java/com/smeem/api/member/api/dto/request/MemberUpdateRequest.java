package com.smeem.api.member.api.dto.request;


import com.smeem.api.member.api.dto.ValidUsername;

public record MemberUpdateRequest(
        @ValidUsername
        String username,
        Boolean termAccepted
) {
}
