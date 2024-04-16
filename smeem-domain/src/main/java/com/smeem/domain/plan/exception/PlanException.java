package com.smeem.domain.plan.exception;

import com.smeem.common.code.failure.FailureCode;
import com.smeem.common.code.failure.PlanFailureCode;
import lombok.Getter;

@Getter
public class PlanException extends RuntimeException {

    private final FailureCode failureCode;

    public PlanException(PlanFailureCode failureCode) {
        super("[PlanException] : " + failureCode.getMessage());
        this.failureCode = failureCode;
    }
}
