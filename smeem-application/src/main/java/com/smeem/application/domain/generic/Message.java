package com.smeem.application.domain.generic;

import lombok.Getter;

@Getter
public class Message {
    // auth
    public static final String SIGNED_IN = "로그인 성공";
    public static final String SIGNED_OUT = "로그아웃 성공";
    public static final String GENERATE_TOKEN = "토큰 발급 성공";
    public static final String WITHDRAW = "회원탈퇴 성공";
}
