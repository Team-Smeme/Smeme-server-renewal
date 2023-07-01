package com.smeme.server.dto.auth;


import com.smeme.server.dto.badge.BadgeResponseDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record SignInResponseDTO(
        String accessToken,
        String refreshToken,
        boolean isRegistered,
        boolean hasPlan,

        List<BadgeResponseDTO> badges
) {

}
