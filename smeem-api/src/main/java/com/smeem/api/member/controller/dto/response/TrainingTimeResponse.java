package com.smeem.api.member.controller.dto.response;

import com.smeem.api.member.service.dto.response.TrainingTimeServiceResponse;

public record TrainingTimeResponse(String day, int hour, int minute) {
    public static TrainingTimeResponse of(TrainingTimeServiceResponse response) {
        return new TrainingTimeResponse(
                response.day(),
                response.hour(),
                response.minute());
    }
}
