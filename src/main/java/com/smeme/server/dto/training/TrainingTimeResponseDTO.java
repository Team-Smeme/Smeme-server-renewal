package com.smeme.server.dto.training;

import lombok.Builder;

@Builder
public record TrainingTimeResponseDTO(String day, int hour, int minute, boolean hasPushAlarm) {
    
}
