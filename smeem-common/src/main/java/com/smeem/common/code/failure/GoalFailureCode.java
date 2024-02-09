package com.smeem.common.code.failure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Getter
public enum GoalFailureCode implements FailureCode {

    /**
     * 404 NOT FOUND
     */
    EMPTY_GOAL(NOT_FOUND, "존재하지 않는 목표입니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
