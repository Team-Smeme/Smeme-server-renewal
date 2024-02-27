package com.smeem.common.code.failure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Getter
public enum BadgeFailureCode implements FailureCode {

    /**
     * 404 NOT FOUND
     */
    INVALID_BADGE(NOT_FOUND, "유효하지 않은 뱃지입니다."),
    EMPTY_BADGE(NOT_FOUND, "존재하지 않는 뱃지입니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
