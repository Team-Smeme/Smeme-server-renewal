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

    /**
     * 404 NOT FOUND
     */
    EMPTY_TRAINING_TIME(NOT_FOUND, "존재하지 않는 학습계획 입니다."),
    NOT_SET_TRAINING_TIME(NOT_FOUND, "학습계획이 설정되지 않았습니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
