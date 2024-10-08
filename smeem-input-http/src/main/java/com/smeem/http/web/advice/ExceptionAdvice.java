package com.smeem.http.web.advice;

import com.smeem.common.logger.HookLogger;
import com.smeem.common.logger.LoggingMessage;
import com.smeem.http.controller.dto.ExceptionResponse;
import com.smeem.common.exception.SmeemException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {
    private final HookLogger hookLogger;

    @ExceptionHandler(SmeemException.class)
    public ResponseEntity<ExceptionResponse> smeemException(SmeemException exception) {
        return ResponseEntity
                .status(exception.statusCode)
                .body(ExceptionResponse.of(exception.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> baseException(RuntimeException exception, WebRequest webRequest) {
        hookLogger.send(LoggingMessage.error(exception, webRequest));
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse.of(exception.getMessage()));
    }
}
