package com.smeme.server.utils;

import lombok.Builder;

@Builder
public record ApiResponse(
        int statusCode,
        boolean isSuccess,
        String message,
        Object data
) {

    public static ApiResponse of(int statusCode, boolean isSuccess, String message, Object data) {
        return ApiResponse.builder()
                .statusCode(statusCode)
                .isSuccess(isSuccess)
                .message(message)
                .data(data)
                .build();
    }

    public static ApiResponse of(int statusCode, boolean isSuccess, String message) {
        return ApiResponse.builder()
                .statusCode(statusCode)
                .isSuccess(isSuccess)
                .message(message)
                .build();
    }
}
