package com.smeem.common.code.failure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Getter
public enum DiaryFailureCode implements FailureCode {

    /**
     * 400 BAD REQUEST
     */
    EXIST_TODAY_DIARY(BAD_REQUEST, "일기는 하루에 한 번씩 작성할 수 있습니다."),

    /**
     * 404 NOT FOUND
     */
    INVALID_DIARY(NOT_FOUND, "유효하지 않은 일기입니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
