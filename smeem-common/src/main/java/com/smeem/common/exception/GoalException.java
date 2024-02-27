package com.smeem.common.exception;


import com.smeem.common.code.failure.FailureCode;
import com.smeem.common.code.failure.GoalFailureCode;
import lombok.Getter;

@Getter
public class GoalException extends RuntimeException {

    private final FailureCode failureCode;

    public GoalException(GoalFailureCode failureCode) {
        super("[GoalException] : " + failureCode.getMessage());
        this.failureCode = failureCode;
    }
}
