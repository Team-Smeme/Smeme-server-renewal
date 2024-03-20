package com.smeem.api.common.advicer;

import com.smeem.api.common.ApiResponseUtil;
import com.smeem.api.common.dto.SuccessResponse;
import com.smeem.common.exception.*;
import com.smeem.external.discord.DiscordAlarmSender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.smeem.common.code.failure.InternalServerFailureCode.SERVER_ERROR;
import static com.smeem.external.discord.DiscordAlarmCase.ERROR;

@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorHandler {

    private final DiscordAlarmSender discordAlarmSender;

    @ExceptionHandler(TrainingTimeException.class)
    public ResponseEntity<SuccessResponse<?>> trainingTimeException(TrainingTimeException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<SuccessResponse<?>> tokenException(TokenException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }

    @ExceptionHandler(BadgeException.class)
    public ResponseEntity<SuccessResponse<?>> badgeException(BadgeException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }

    @ExceptionHandler(DiaryException.class)
    public ResponseEntity<SuccessResponse<?>> diaryException(DiaryException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<SuccessResponse<?>> memberException(MemberException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }

    @ExceptionHandler(GoalException.class)
    public ResponseEntity<SuccessResponse<?>> goalException(GoalException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }

    @ExceptionHandler(TopicException.class)
    public ResponseEntity<SuccessResponse<?>> topicException(TopicException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }

    @ExceptionHandler(AppleException.class)
    public ResponseEntity<SuccessResponse<?>> appleException(AppleException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }

    @ExceptionHandler(FcmException.class)
    public ResponseEntity<SuccessResponse<?>> fcmException(FcmException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<SuccessResponse<?>> authException(AuthException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<SuccessResponse<?>> baseException(RuntimeException exception) {
        sendDiscordAlarm(exception);
        return ApiResponseUtil.failure(SERVER_ERROR);
    }

    private void sendDiscordAlarm(RuntimeException exception) {
            discordAlarmSender.send(exception.getMessage(), ERROR);
    }
}
