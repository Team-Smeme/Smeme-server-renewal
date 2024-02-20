package com.smeem.common.code.failure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Getter
public enum TrainingTimeFailureCode implements FailureCode {

    /**
     * 400 BAD REQUEST
     */
    INVALID_DAY_OF_WEEK(BAD_REQUEST, "유효하지 않은 요일 값입니다."),
    INVALID_HOUR(BAD_REQUEST, "시(hour)는 1 이상 24 이하이어야 합니다."),
    INVALID_MINUTE(BAD_REQUEST, "분(minute)은 30분 단위이어야 합니다. (0 또는 30)"),

    /**
     * 404 NOT FOUND
     */
    EMPTY_TRAINING_TIME(NOT_FOUND, "존재하지 않는 학습계획 입니다."),
    NOT_SET_TRAINING_TIME(NOT_FOUND, "학습계획이 설정되지 않았습니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
