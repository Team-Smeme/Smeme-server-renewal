package com.smeme.server.dto.badge;

public record BadgeResponseDTO(Long id, String name, String type, String imageUrl) {
    public static BadgeResponseDTO of(Long id, String name, String type, String imageUrl) {
        return new BadgeResponseDTO(id, name, type, imageUrl);
    }
}
