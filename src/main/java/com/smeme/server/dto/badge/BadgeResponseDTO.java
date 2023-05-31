package com.smeme.server.dto.badge;
public record BadgeResponseDTO(Long id, String name, String imageUrl) {
    public static BadgeResponseDTO of (Long id, String name, String imageUrl) {
        return new BadgeResponseDTO(id, name, imageUrl);
    }
}
