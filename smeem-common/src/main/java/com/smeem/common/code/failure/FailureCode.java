package com.smeem.common.code.failure;

import org.springframework.http.HttpStatus;

public interface FailureCode {
    HttpStatus getStatus();
    String getMessage();
}
