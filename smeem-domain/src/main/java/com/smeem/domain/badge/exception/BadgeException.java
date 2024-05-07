package com.smeem.domain.badge.exception;

import com.smeem.common.code.failure.BadgeFailureCode;
import com.smeem.common.code.failure.FailureCode;
import lombok.Getter;

@Getter
public class BadgeException extends RuntimeException {

    private final FailureCode failureCode;

    public BadgeException(BadgeFailureCode failureCode) {
        super("[BadgeException] : " + failureCode.getMessage());
        this.failureCode = failureCode;
    }
}
