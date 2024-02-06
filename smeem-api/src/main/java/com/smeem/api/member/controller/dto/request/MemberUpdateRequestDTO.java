package com.smeem.api.member.controller.dto.request;


import com.smeem.api.member.controller.dto.ValidUsername;

public record MemberUpdateRequestDTO(
        @ValidUsername
        String username,
        Boolean termAccepted
) {
}
