package com.smeme.server.util.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessage {

    // jwt
    INVALID_TOKEN("유효햐지 않은 토큰");

    private String message;
}
