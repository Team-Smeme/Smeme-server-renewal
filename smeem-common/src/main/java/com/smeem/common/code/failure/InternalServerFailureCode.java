package com.smeem.common.code.failure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
@RequiredArgsConstructor
public enum InternalServerFailureCode implements  FailureCode {

    /*
    * 500 INTERNAL_SERVER_ERROR
    */

    SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 내부 오류"),
    ;

    private final HttpStatus status;
    private final String message;

}
