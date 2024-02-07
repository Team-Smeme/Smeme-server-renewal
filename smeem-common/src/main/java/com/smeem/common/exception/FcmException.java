package com.smeem.common.exception;

import com.smeem.common.code.failure.FailureCode;
import com.smeem.common.code.failure.FcmFailureCode;
import lombok.Getter;

@Getter
public class FcmException extends RuntimeException {

    private final FailureCode failureCode;

    public FcmException(FcmFailureCode failureCode) {
        super("[FcmException] : " + failureCode.getMessage());
        this.failureCode = failureCode;
    }
}
