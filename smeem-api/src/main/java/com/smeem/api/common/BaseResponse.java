package com.smeem.api.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record BaseResponse<T>(
        boolean success,
        String message,
        @JsonInclude(value = NON_NULL)
        T data
) {

    public static <T> BaseResponse<T> of(String message, T data) {
        return BaseResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static BaseResponse<?> of(boolean isSuccess, String message) {
        return BaseResponse.builder()
                .success(isSuccess)
                .message(message)
                .build();
    }
}
