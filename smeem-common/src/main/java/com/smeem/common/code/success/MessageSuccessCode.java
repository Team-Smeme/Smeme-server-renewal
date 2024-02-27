package com.smeem.common.code.success;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@Getter
public enum MessageSuccessCode implements SuccessCode {

    /**
     * 200 Ok
     */
    SUCCESS_PUSH_MESSAGE(OK, "푸시알람 요청 성공"),
    ;

    private final HttpStatus status;
    private final String message;
}
