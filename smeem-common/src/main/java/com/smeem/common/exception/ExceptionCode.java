package com.smeem.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {
    // 4xx
    UNAUTHORIZED(401, "유효하지 않은 토큰"),
    NOT_FOUND(404, "존재하지 않음")

    ;

    private final int statusCode;
    private final String message;
}
