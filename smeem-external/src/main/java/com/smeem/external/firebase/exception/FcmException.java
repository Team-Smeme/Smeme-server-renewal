package com.smeem.external.firebase.exception;

import com.smeem.common.code.failure.FailureCode;
import com.smeem.common.code.failure.FcmFailureCode;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class FcmException extends RuntimeException {

    private final FailureCode failureCode;

    public FcmException(FcmFailureCode failureCode, String message) {
        super("[FcmException] : " + message);
        this.failureCode = failureCode;
    }
}
