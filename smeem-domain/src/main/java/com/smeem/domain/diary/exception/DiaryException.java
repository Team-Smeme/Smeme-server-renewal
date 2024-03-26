package com.smeem.domain.diary.exception;


import com.smeem.common.code.failure.DiaryFailureCode;
import com.smeem.common.code.failure.FailureCode;
import lombok.Getter;

@Getter
public class DiaryException extends RuntimeException {

    private final FailureCode failureCode;

    public DiaryException(DiaryFailureCode failureCode) {
        super("[DiaryException] : " + failureCode.getMessage());
        this.failureCode = failureCode;
    }
}
