package com.smeem.member.controller.dto.request;

import com.smeem.member.controller.dto.ValidUsername;

public record MemberUpdateRequestDTO(
        @ValidUsername
        String username,
        Boolean termAccepted
) {
}
