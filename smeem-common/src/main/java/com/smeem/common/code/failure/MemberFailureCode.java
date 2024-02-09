package com.smeem.common.code.failure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@Getter
public enum MemberFailureCode implements FailureCode {

    /**
     * 400 BAD REQUEST
     */
    DUPLICATE_USERNAME(BAD_REQUEST, "이미 존재하는 닉네임입니다."),
    INVALID_USERNAME(BAD_REQUEST, "유효하지 않은 닉네임입니다."),

    /**
     * 404 NOT FOUND
     */
    EMPTY_MEMBER(NOT_FOUND, "존재하지 않는 회원입니다."),
    INVALID_MEMBER(NOT_FOUND, "유효하지 않은 회원입니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
