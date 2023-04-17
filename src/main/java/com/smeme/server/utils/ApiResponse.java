package com.smeme.server.utils;

import org.springframework.http.HttpStatus;

import com.google.api.gax.httpjson.HttpJsonStatusCode;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record ApiResponse(
        int status,
        boolean success,
        @NonNull String message,
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

    public static ApiResponse success(String message) {
        return ApiResponse.builder()
            .status(HttpStatus.OK.value())
            .success(true)
            .message(message)
            .build();
    }
}
