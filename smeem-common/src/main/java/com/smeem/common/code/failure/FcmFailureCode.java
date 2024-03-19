package com.smeem.common.code.failure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

@RequiredArgsConstructor
@Getter
public enum FcmFailureCode implements FailureCode {

    /**
     * 400 BAD REQUEST
     */
    INVALID_REQUEST_PATTERN(BAD_REQUEST, "잘못된 요청 형식입니다."),
    INVALID_REQUEST_MESSAGE(BAD_REQUEST, "잘못된 fcm 요청입니다."),

    /**
     * 405 METHOD NOT ALLOW
     */
    INVALID_REQUEST_URI(METHOD_NOT_ALLOWED, "외부 API 요청에 실패했습니다"),
    ;

    private final HttpStatus status;
    private final String message;
}
