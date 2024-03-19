package com.smeem.common.code.success;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@Getter
public enum AuthSuccessCode implements SuccessCode {

    /**
     * 200 Ok
     */
    SUCCESS_SIGNIN(OK, "소셜로그인 성공"),
    SUCCESS_SIGNOUT(OK, "로그아웃 성공"),
    SUCCESS_WITHDRAW(OK, "회원 탈퇴 성공"),
    ;

    private final HttpStatus status;
    private final String message;
}
