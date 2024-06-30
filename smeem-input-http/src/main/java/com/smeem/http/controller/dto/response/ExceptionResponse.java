package com.smeem.http.controller.dto.response;

import com.smeem.http.exception.ExceptionCode;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record ExceptionResponse(
        boolean success,
        String message
) {

    public static ExceptionResponse of(ExceptionCode exceptionCode) {
        return ExceptionResponse.builder()
                .success(false)
                .message(exceptionCode.getMessage())
                .build();
    }
}
