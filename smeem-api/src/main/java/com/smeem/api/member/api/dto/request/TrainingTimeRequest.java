package com.smeem.api.member.api.dto.request;


public record TrainingTimeRequest(
        String day,
        Integer hour,
        Integer minute
) {
}
