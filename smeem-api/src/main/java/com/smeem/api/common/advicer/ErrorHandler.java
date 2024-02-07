package com.smeem.api.common.advicer;

import com.smeem.api.common.ApiResponseUtil;
import com.smeem.api.common.BaseResponse;
import com.smeem.common.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(TrainingTimeException.class)
    public ResponseEntity<BaseResponse<?>> trainingTimeException(TrainingTimeException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<BaseResponse<?>> tokenException(TokenException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }

    @ExceptionHandler(BadgeException.class)
    public ResponseEntity<BaseResponse<?>> badgeException(BadgeException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }

    @ExceptionHandler(DiaryException.class)
    public ResponseEntity<BaseResponse<?>> diaryException(DiaryException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<BaseResponse<?>> memberException(MemberException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }

    @ExceptionHandler(GoalException.class)
    public ResponseEntity<BaseResponse<?>> goalException(GoalException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }

    @ExceptionHandler(TopicException.class)
    public ResponseEntity<BaseResponse<?>> topicException(TopicException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }

    @ExceptionHandler(AppleException.class)
    public ResponseEntity<BaseResponse<?>> appleException(AppleException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }
}
