package com.smeem.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {
    // 4xx
    UNAUTHORIZED(401, "유효하지 않은 토큰 "),
    NOT_FOUND(404, "존재하지 않음 "),
    TOO_MANY_REQUESTS(429, "너무 많은 요청"),

    // 5xx
    SERVICE_AVAILABLE(503, "서비스에 접근할 수 없음 "),
    OPEN_AI_SERVICE_AVAILABLE(503, "OpenAI 서비스 에러"),
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류"),
    JSON_PARSE_ERROR(500, "json 파싱 오류. 서버에게 문의해주세요."),
    ;

    private final int statusCode;
    private final String message;
}
