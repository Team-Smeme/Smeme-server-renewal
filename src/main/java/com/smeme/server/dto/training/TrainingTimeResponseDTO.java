package com.smeme.server.dto.training;

public record TrainingTimeResponseDTO(String day, int hour, int minute) {
    public static TrainingTimeResponseDTO of(String day, int hour, int minute) {
        return new TrainingTimeResponseDTO(day, hour, minute);
    }
}
