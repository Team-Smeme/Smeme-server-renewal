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
    FAIL_APPLE_REQUEST(UNAUTHORIZED, "애플 로그인에 실패했습니다."),
    FAIL_KAKAO_REQUEST(UNAUTHORIZED, "카카오 로그인에 실패했습니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
