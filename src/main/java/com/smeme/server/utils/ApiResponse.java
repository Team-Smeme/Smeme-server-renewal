package com.smeme.server.utils;

import lombok.Builder;

@Builder
public record ApiResponse(
        int status,
        boolean success,
        String message,
        Object data
) {

    public static ApiResponse of(int status, boolean success, String message, Object data) {
        return ApiResponse.builder()
                .status(status)
                .success(success)
                .message(message)
                .data(data)
                .build();
    }

    public static ApiResponse of(int status, boolean success, String message) {
        return ApiResponse.builder()
                .status(status)
                .success(success)
                .message(message)
                .build();
    }
}
