package com.smeem.common.code.success;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@Getter
public enum BadgeSuccessCode implements SuccessCode {

    /**
     * 200 Ok
     */
    SUCCESS_GET_BADGES(OK, "뱃지 리스트 조회 성공"),
    ;

    private final HttpStatus status;
    private final String message;
}
