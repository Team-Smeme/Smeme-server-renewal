package com.smeme.server.util;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record ApiResponse(
	boolean success,
	@NonNull
	String message,
	Object data
) {

    public static ApiResponse success(String message, Object data) {
        return ApiResponse.builder()
            .success(true)
            .message(message)
            .data(data)
            .build();
    }

    public static ApiResponse success(String message) {
        return ApiResponse.builder()
            .success(true)
            .message(message)
            .build();
    }

    public static ApiResponse fail(String message) {
        return ApiResponse.builder()
            .success(false)
            .message(message)
            .build();
    }
}
