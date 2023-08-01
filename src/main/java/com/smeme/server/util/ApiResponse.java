package com.smeme.server.util;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;

@Schema(description = "API 요청 응답 DTO")
@Builder
public record ApiResponse(
	@Schema(description = "성공 여부")
	boolean success,

	@Schema(description = "응답 메세지")
	@NonNull
	String message,

	@Schema(description = " 응답 데이터 || Null")
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
