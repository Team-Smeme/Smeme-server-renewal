package com.smeem.common.code.success;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@Getter
public enum TokenSuccessCode implements SuccessCode {

    /**
     * 200 Ok
     */
    SUCCESS_ISSUE_TOKEN(OK, "토큰 발급 성공"),
    ;

    private final HttpStatus status;
    private final String message;
}
