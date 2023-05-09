package com.smeme.server.util.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessage {

    // jwt
    INVALID_TOKEN("유효하지 않은 토큰"),
    EMPTY_REFRESH_TOKEN("리프레시 토큰이 없습니다"),

    // member
    INVALID_MEMBER("유효하지 않은 회원입니다.");

    private String message;
}
