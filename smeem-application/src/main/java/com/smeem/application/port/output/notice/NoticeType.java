package com.smeem.application.port.output.notice;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NoticeType {
    SIGN_IN("회원가입 알리미"),
    WITHDRAW("회원탈퇴 알리미"),
    ERROR("에러 알리미"),
    ;

    private final String name;
}
