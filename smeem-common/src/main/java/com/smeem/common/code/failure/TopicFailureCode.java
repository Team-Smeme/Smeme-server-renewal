package com.smeem.common.code.failure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Getter
public enum TopicFailureCode implements FailureCode {

    /**
     * 404 NOT FOUND
     */
    INVALID_TOPIC(NOT_FOUND, "유효하지 않은 랜덤주제입니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
