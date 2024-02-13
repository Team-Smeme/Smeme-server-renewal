package com.smeem.common.exception;

import com.smeem.common.code.failure.FailureCode;
import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {

    private final FailureCode failureCode;

    public AuthException(FailureCode failureCode) {
        super("[AuthException] : " + failureCode.getMessage());
        this.failureCode = failureCode;
    }
}