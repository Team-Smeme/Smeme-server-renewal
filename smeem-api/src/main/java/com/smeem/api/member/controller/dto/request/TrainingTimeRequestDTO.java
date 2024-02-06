package com.smeem.api.member.controller.dto.request;

import jakarta.validation.constraints.NotNull;

public record TrainingTimeRequestDTO(
        @NotNull
        String day,
        @NotNull
        int hour,
        @NotNull
        int minute
) {
}
