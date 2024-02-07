package com.smeem.common.exception;

import com.smeem.common.code.failure.FailureCode;
import lombok.Getter;

@Getter
public class AppleException extends RuntimeException {

    private final FailureCode failureCode;

    public AppleException(FailureCode failureCode) {
        super("[AppleException] : " + failureCode.getMessage());
        this.failureCode = failureCode;
    }
}
