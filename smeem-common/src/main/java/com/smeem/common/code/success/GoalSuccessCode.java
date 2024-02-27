package com.smeem.common.code.success;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@Getter
public enum GoalSuccessCode implements SuccessCode {

    /**
     * 200 Ok
     */
    SUCCESS_GET_GOALS(OK, "학습 목표 리스트 조회 성공"),
    SUCCESS_GET_GOAL(OK, "학습 목표 조회 성공");
    ;

    private final HttpStatus status;
    private final String message;
}
