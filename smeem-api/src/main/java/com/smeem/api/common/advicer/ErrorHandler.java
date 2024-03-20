package com.smeem.api.common.advicer;

import com.smeem.api.common.ApiResponseUtil;
import com.smeem.api.common.dto.FailureResponse;
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
    public ResponseEntity<FailureResponse> trainingTimeException(TrainingTimeException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<FailureResponse> tokenException(TokenException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }

    @ExceptionHandler(BadgeException.class)
    public ResponseEntity<FailureResponse> badgeException(BadgeException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }

    @ExceptionHandler(DiaryException.class)
    public ResponseEntity<FailureResponse> diaryException(DiaryException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<FailureResponse> memberException(MemberException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }

    @ExceptionHandler(GoalException.class)
    public ResponseEntity<FailureResponse> goalException(GoalException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }

    @ExceptionHandler(TopicException.class)
    public ResponseEntity<FailureResponse> topicException(TopicException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }

    @ExceptionHandler(AppleException.class)
    public ResponseEntity<FailureResponse> appleException(AppleException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }

    @ExceptionHandler(FcmException.class)
    public ResponseEntity<FailureResponse> fcmException(FcmException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<FailureResponse> authException(AuthException exception) {
        return ApiResponseUtil.failure(exception.getFailureCode());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<FailureResponse> baseException(RuntimeException exception) {
        sendDiscordAlarm(exception);
        return ApiResponseUtil.failure(SERVER_ERROR);
    }

    private void sendDiscordAlarm(RuntimeException exception) {
            discordAlarmSender.send(exception.getMessage(), ERROR);
    }
}
