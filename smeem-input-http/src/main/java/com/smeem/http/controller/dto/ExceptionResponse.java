package com.smeem.http.controller.dto;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record ExceptionResponse(
        boolean success,
        String message
) {

    public static ExceptionResponse of(String message) {
        return ExceptionResponse.builder()
                .success(false)
                .message(message)
                .build();
    }
}
