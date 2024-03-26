package com.smeem.external.oauth.exception;

import com.smeem.common.code.failure.AuthFailureCode;
import com.smeem.common.code.failure.FailureCode;
import lombok.Getter;

@Getter
public class TokenException extends SecurityException {

    private final FailureCode failureCode;

    public TokenException(AuthFailureCode failureCode) {
        super("[TokenException] : " + failureCode.getMessage());
        this.failureCode = failureCode;
    }
}
