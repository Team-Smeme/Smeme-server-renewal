package com.smeme.server.dto.badge;


import java.util.List;

public record BadgeListResponseDTO(List<BadgeResponseDTO> badges) {
    public static BadgeListResponseDTO of(List<BadgeResponseDTO> badges) {
        return new BadgeListResponseDTO(badges);
    }

}