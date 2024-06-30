package com.smeem.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode { //TODO: common 모듈로 이동
    // 4xx
    UNAUTHORIZED(401, "유효하지 않은 토큰"),

    ;

    private final int statusCode;
    private final String message;
}
