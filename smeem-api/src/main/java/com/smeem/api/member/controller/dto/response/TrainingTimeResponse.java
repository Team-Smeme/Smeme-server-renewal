package com.smeem.api.member.controller.dto.response;

import com.smeem.api.member.service.dto.response.TrainingTimeServiceResponse;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record TrainingTimeResponse(
        String day,
        int hour,
        int minute
) {
    public static TrainingTimeResponse of(TrainingTimeServiceResponse response) {
        return TrainingTimeResponse.builder()
                .day(response.day())
                .hour(response.hour())
                .minute(response.minute())
                .build();
    }
}
