package com.smeem.api.common;

import com.smeem.common.code.failure.FailureCode;
import com.smeem.common.code.success.SuccessCode;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public interface ApiResponseUtil {

    static ResponseEntity<BaseResponse<?>> success(SuccessCode code) {
        return ResponseEntity
                .status(code.getStatus())
                .body(BaseResponse.of(true, code.getMessage()));
    }

    static <T> ResponseEntity<BaseResponse<?>> success(SuccessCode code, T data) {
        return ResponseEntity
                .status(code.getStatus())
                .body(BaseResponse.of(code.getMessage(), data));
    }

    static <T> ResponseEntity<BaseResponse<?>> success(SuccessCode code, URI uri, T data) {
        return ResponseEntity
                .status(code.getStatus())
                .location(uri)
                .body(BaseResponse.of(code.getMessage(), data));
    }

    static ResponseEntity<BaseResponse<?>> failure(FailureCode code) {
        return ResponseEntity
                .status(code.getStatus())
                .body(BaseResponse.of(true, code.getMessage()));
    }
}
