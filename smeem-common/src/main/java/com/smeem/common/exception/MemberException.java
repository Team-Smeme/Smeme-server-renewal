package com.smeem.common.exception;


import com.smeem.common.code.failure.FailureCode;
import com.smeem.common.code.failure.MemberFailureCode;
import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {

    private final FailureCode failureCode;

    public MemberException(MemberFailureCode failureCode) {
        super("[MemberException] : " + failureCode.getMessage());
        this.failureCode = failureCode;
    }
}
