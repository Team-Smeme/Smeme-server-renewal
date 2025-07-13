package com.smeem.application.domain.generic;

import lombok.Getter;

@Getter
public class SmeemMessage {
    // general
    public static final String SUCCESS_REQUEST = "요청처리 성공";

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
    public static final String COACH_DIARY = "일기 코칭 성공";

    // goal
    public static final String RETRIEVE_GOAL = "목표 조회 성공";

    // member
    public static final String UPDATE_MEMBER = "회원 수정 성공";
    public static final String RETRIEVE_MEMBER = "회원 조회 성공";

    // plan
    public static final String RETRIEVE_PLAN = "플랜 조회 성공";

    // test
    public static final String TEST = "테스트 성공";

    // topic
    public static final String RETRIEVE_TOPIC = "일기 주제 조회 성공";

    // version
    public static final String RETRIEVE_VERSION = "버전 조회 성공";

    // survey
    public static final String SURVEY_COACHING = "만족도 조사 결과 보내기 성공";
}
