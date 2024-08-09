package com.smeem.common.logger;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoggerType {
    SIGN_IN("회원가입 알리미"),
    WITHDRAW("회원탈퇴 알리미"),
    ERROR("에러 알리미"),
    ;

    private final String name;
}
