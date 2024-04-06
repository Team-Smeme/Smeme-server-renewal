package com.smeem.api.support;

import com.smeem.api.common.FailureResponse;
import com.smeem.api.common.SuccessResponse;
import com.smeem.common.code.failure.FailureCode;
import com.smeem.common.code.success.SuccessCode;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public interface ApiResponseGenerator {

    static ResponseEntity<SuccessResponse<?>> success(SuccessCode code) {
        return ResponseEntity
                .status(code.getStatus())
                .body(SuccessResponse.of(code.getMessage()));
    }

    static <T> ResponseEntity<SuccessResponse<T>> success(SuccessCode code, T data) {
        return ResponseEntity
                .status(code.getStatus())
                .body(SuccessResponse.of(code.getMessage(), data));
    }

    static <T> ResponseEntity<SuccessResponse<T>> success(SuccessCode code, URI uri, T data) {
        return ResponseEntity
                .status(code.getStatus())
                .location(uri)
                .body(SuccessResponse.of(code.getMessage(), data));
    }

    static ResponseEntity<FailureResponse> failure(FailureCode code) {
        return ResponseEntity
                .status(code.getStatus())
                .body(FailureResponse.of(code.getMessage()));
    }

    static ResponseEntity<FailureResponse> failure(FailureCode code, String message) {
        return ResponseEntity
                .status(code.getStatus())
                .body(FailureResponse.of(message));
    }
}
