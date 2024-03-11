package com.smeem.api.member.controller.dto.request;


public record TrainingTimeRequest(
        String day,
        Integer hour,
        Integer minute
) {
}
