package com.smeem.api.common;

import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record ErrorResponse(
        boolean success,
        String message
) {

    public static ErrorResponse of(String message) {
        return new ErrorResponse(false, message);
    }
}
