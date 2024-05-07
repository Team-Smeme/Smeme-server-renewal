package com.smeem.common.code.failure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@Getter
public enum FcmFailureCode implements FailureCode {

    /**
     * 503 Service Unavailable
     */
    FCM_SERVICE_UNAVAILABLE(SERVICE_UNAVAILABLE, "FCM 서버에서 서비스를 제공할 수 없습니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
