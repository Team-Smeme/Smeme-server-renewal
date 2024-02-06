package com.smeem.api.common;

import com.smeem.common.code.success.SuccessCode;
import org.springframework.http.ResponseEntity;

public interface ApiResponseUtil {

    static <T> ResponseEntity<BaseResponse<?>> success(SuccessCode code, T data) {
        return ResponseEntity
                .status(code.getStatus())
                .body(BaseResponse.of(code.getMessage(), data));
    }

    static ResponseEntity<BaseResponse<?>> success(SuccessCode code) {
        return ResponseEntity
                .status(code.getStatus())
                .body(BaseResponse.of(true, code.getMessage()));
    }

//    static <T> ResponseEntity<BaseResponse<?>> failure(FailureCode code) {
//        return ResponseEntity
//                .status(code.getStatus())
//                .body(BaseResponse.of(false, code.getMessage()));
//    }
}
