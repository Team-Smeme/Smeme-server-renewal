package com.smeem.common.code.success;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@Getter
public enum DiarySuccessCode implements SuccessCode {

    /**
     * 200 Ok
     */
    SUCCESS_GET_DIARY(OK, "일기 상세 조회 성공"),
    SUCCESS_UPDATE_DAIRY(OK, "일기 수정 성공"),
    SUCCESS_DELETE_DIARY(OK, "일기 삭제 성공"),
    SUCCESS_GET_DIARIES(OK, "일기 리스트 조회 성공"),

    /**
     * 201 Created
     */
    SUCCESS_CREATE_DIARY(CREATED, "일기 생성 성공"),
    ;

    private final HttpStatus status;
    private final String message;
}
