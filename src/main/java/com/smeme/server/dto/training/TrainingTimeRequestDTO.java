package com.smeme.server.dto.training;

import jakarta.validation.constraints.NotNull;

public record TrainingTimeRequestDTO(

        @NotNull
        String day,

        int hour,

        int minute
) {
}
