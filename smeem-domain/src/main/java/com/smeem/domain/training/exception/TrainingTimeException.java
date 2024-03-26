package com.smeem.domain.training.exception;

import com.smeem.common.code.failure.FailureCode;
import com.smeem.common.code.failure.TrainingTimeFailureCode;
import lombok.Getter;

@Getter
public class TrainingTimeException extends RuntimeException {

    private final FailureCode failureCode;

    public TrainingTimeException(TrainingTimeFailureCode failureCode) {
        super("[TrainingTimeException] : " + failureCode.getMessage());
        this.failureCode = failureCode;
    }
}
