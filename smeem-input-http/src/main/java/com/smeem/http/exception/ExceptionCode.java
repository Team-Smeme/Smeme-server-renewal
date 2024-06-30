package com.smeem.http.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode { //TODO: common 모듈로 이동
    // 4xx
    UNAUTHORIZED(401, "인증된 토큰으로부터의 요청이 아닙니다."),

    ;

    private final int statusCode;
    private final String message;
}
