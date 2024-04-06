package com.smeem.domain.topic.exception;

import com.smeem.common.code.failure.FailureCode;
import com.smeem.common.code.failure.TopicFailureCode;
import lombok.Getter;

@Getter
public class TopicException extends RuntimeException {

    private final FailureCode failureCode;

    public TopicException(TopicFailureCode failureCode) {
        super("[TopicException] : " + failureCode.getMessage());
        this.failureCode = failureCode;
    }
}
