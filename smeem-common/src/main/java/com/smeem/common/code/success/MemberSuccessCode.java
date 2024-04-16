package com.smeem.common.code.success;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@Getter
public enum MemberSuccessCode implements SuccessCode {

    /**
     * 200 Ok
     */
    SUCCESS_UPDATE_USERNAME(OK, "닉네임 변경 성공"),
    SUCCESS_GET_USER(OK, "회원 정보 조회 성공"),
    SUCCESS_UPDATE_USER_PLAN(OK, "회원 학습 계획 업데이트 성공"),
    SUCCESS_CHECK_DUPLICATED_NAME(OK, "닉네임 중복 검사 성공"),
    SUCCESS_UPDATE_USER_PUSH(OK, "회원 푸시알람 동의여부 업데이트 성공"),
    SUCCESS_GET_PERFORMANCE_SUMMARY(OK, "성과 요약 조회 성공"),
    SUCCESS_UPDATE_VISIT_TODAY(OK, "방문 체크 성공"),
    ;

    private final HttpStatus status;
    private final String message;
}
