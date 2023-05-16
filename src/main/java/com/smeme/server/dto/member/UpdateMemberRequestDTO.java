package com.smeme.server.dto.member;

public record UpdateMemberRequestDTO(
        @ValidUsername
        String username,
        boolean termAccepted
) {
}
