package com.smeem.api.member.service.dto.response;


import lombok.Builder;

import static lombok.AccessLevel.*;

@Builder(access = PRIVATE)
public record TrainingTimeServiceResponse(String day, int hour, int minute) {
    public static TrainingTimeServiceResponse of(String day, int hour, int minute) {
        return TrainingTimeServiceResponse.builder()
                .day(day)
                .hour(hour)
                .minute(minute)
                .build();
    }
}