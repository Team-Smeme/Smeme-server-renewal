package com.smeem.common.code.failure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
@Getter
public enum AuthFailureCode implements FailureCode {

    /**
     * 401 UNAUTHORIZED
     */
    EMPTY_ACCESS_TOKEN(UNAUTHORIZED, "액세스 토큰이 없습니다."),
    INVALID_TOKEN(UNAUTHORIZED, "유효하지 않은 토큰입니다"),
    INVALID_APPLE_TOKEN(UNAUTHORIZED, "유효하지 않은 애플 토큰입니다.")
    ;

    private final HttpStatus status;
    private final String message;
}
