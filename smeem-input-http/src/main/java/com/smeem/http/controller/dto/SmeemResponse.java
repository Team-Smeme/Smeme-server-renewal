package com.smeem.http.controller.dto;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record SmeemResponse<T>(
        boolean success,
        String message,
        T data
) {

    public static <T> SmeemResponse<T> of(T data, String message) {
        return SmeemResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static SmeemResponse<?> of(String message) {
        return SmeemResponse.builder()
                .success(true)
                .message(message)
                .build();
    }
}
