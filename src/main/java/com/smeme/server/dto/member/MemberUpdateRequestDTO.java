package com.smeme.server.dto.member;

public record MemberUpdateRequestDTO(
        @ValidUsername
        String username,
        boolean termAccepted
) {
}
