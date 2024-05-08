package com.smeem.api.support;

import com.smeem.api.common.FailureResponse;
import com.smeem.domain.badge.exception.BadgeException;
import com.smeem.domain.diary.exception.DiaryException;
import com.smeem.domain.goal.exception.GoalException;
import com.smeem.domain.member.exception.MemberException;
import com.smeem.domain.plan.exception.PlanException;
import com.smeem.domain.topic.exception.TopicException;
import com.smeem.domain.training.exception.TrainingTimeException;
import com.smeem.external.discord.AlarmService;
import com.smeem.external.firebase.exception.FcmException;
import com.smeem.external.oauth.apple.exception.AppleException;
import com.smeem.external.oauth.exception.AuthException;
import com.smeem.external.oauth.exception.TokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.smeem.common.code.failure.InternalServerFailureCode.SERVER_ERROR;
import static com.smeem.external.discord.DiscordAlarmCase.ERROR;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionInterceptor {

    private final AlarmService alarmService;

    @ExceptionHandler(TrainingTimeException.class)
    public ResponseEntity<FailureResponse> trainingTimeException(TrainingTimeException exception) {
        return ApiResponseGenerator.failure(exception.getFailureCode());
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<FailureResponse> tokenException(TokenException exception) {
        return ApiResponseGenerator.failure(exception.getFailureCode());
    }

    @ExceptionHandler(BadgeException.class)
    public ResponseEntity<FailureResponse> badgeException(BadgeException exception) {
        return ApiResponseGenerator.failure(exception.getFailureCode());
    }

    @ExceptionHandler(DiaryException.class)
    public ResponseEntity<FailureResponse> diaryException(DiaryException exception) {
        return ApiResponseGenerator.failure(exception.getFailureCode());
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<FailureResponse> memberException(MemberException exception) {
        return ApiResponseGenerator.failure(exception.getFailureCode());
    }

    @ExceptionHandler(GoalException.class)
    public ResponseEntity<FailureResponse> goalException(GoalException exception) {
        return ApiResponseGenerator.failure(exception.getFailureCode());
    }

    @ExceptionHandler(TopicException.class)
    public ResponseEntity<FailureResponse> topicException(TopicException exception) {
        return ApiResponseGenerator.failure(exception.getFailureCode());
    }

    @ExceptionHandler(AppleException.class)
    public ResponseEntity<FailureResponse> appleException(AppleException exception) {
        return ApiResponseGenerator.failure(exception.getFailureCode());
    }

    @ExceptionHandler(FcmException.class)
    public ResponseEntity<FailureResponse> fcmException(FcmException exception) {
        return ApiResponseGenerator.failure(exception.getFailureCode());
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<FailureResponse> authException(AuthException exception) {
        return ApiResponseGenerator.failure(exception.getFailureCode());
    }

    @ExceptionHandler(PlanException.class)
    public ResponseEntity<FailureResponse> authException(PlanException exception) {
        return ApiResponseGenerator.failure(exception.getFailureCode());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<FailureResponse> baseException(RuntimeException exception) {
        sendDiscordAlarm(exception);
        return ApiResponseGenerator.failure(SERVER_ERROR);
    }

    private void sendDiscordAlarm(RuntimeException exception) {
            alarmService.send(exception.getMessage(), ERROR);
    }
}
