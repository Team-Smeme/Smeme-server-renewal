package com.smeme.server.dto.training;

public record TrainingTimeRequestDTO(
        String day,
        int hour,
        int minute
) {
}
