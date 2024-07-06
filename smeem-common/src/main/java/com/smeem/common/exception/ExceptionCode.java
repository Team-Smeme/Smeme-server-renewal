package com.smeem.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {
    // 4xx
    UNAUTHORIZED(401, "유효하지 않은 토큰 "),
    NOT_FOUND(404, "존재하지 않음 "),

    // 5xx
    SERVICE_AVAILABLE(503, "서비스에 접근할 수 없음 "),
    ;

    private final int statusCode;
    private final String message;
}
