package com.smeem.api.common;

import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record FailureResponse(
        boolean success,
        String message
) {

    public static FailureResponse of(String message) {
        return FailureResponse.builder()
                .success(false)
                .message(message)
                .build();
    }
}
