package com.smeem.common.code.success;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@Getter
public enum TestSuccessCode implements SuccessCode {

    /**
     * 200 Ok
     */
    SUCCESS_SERVER_CONNECT(OK, "서버 연결 성공"),
    SUCCESS_SEND_PUSH_ALARM(OK, "푸시 알람 전송 성공"),
    ;

    private final HttpStatus status;
    private final String message;
}
