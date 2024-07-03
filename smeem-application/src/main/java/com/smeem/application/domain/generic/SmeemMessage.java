package com.smeem.application.domain.generic;

import lombok.Getter;

@Getter
public class SmeemMessage {
    // auth
    public static final String SIGNED_IN = "로그인 성공";
    public static final String SIGNED_OUT = "로그아웃 성공";
    public static final String GENERATE_TOKEN = "토큰 발급 성공";
    public static final String WITHDRAW = "회원탈퇴 성공";

    // badge
    public static final String RETRIEVE_BADGE = "배지 조회 성공";

    // diary
    public static final String WRITE_DIARY = "일기 작성 성공";
    public static final String MODIFY_DIARY = "일기 수정 성공";
    public static final String RETRIEVE_DIARY = "일기 조회 성공";
    public static final String DELETE_MESSAGE = "일기 삭제 성공";

    // goal
    public static final String RETRIEVE_GOAL = "목표 조회 성공";
}
