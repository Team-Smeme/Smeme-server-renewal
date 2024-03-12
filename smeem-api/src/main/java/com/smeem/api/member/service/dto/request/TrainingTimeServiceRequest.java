package com.smeem.api.member.service.dto.request;


import com.smeem.api.member.api.dto.request.TrainingTimeRequest;
import lombok.Builder;

import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record TrainingTimeServiceRequest(
        String day,
        Integer hour,
        Integer minute
) {
    public static TrainingTimeServiceRequest of(TrainingTimeRequest request) {
        if (Objects.isNull(request)) {
            return null;
        }
        return TrainingTimeServiceRequest.builder()
                .day(request.day())
                .hour(request.hour())
                .minute(request.minute())
                .build();
    }
}
