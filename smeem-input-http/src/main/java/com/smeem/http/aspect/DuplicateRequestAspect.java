package com.smeem.http.aspect;

import com.smeem.common.exception.ExceptionCode;
import com.smeem.http.controller.dto.ExceptionResponse;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Aspect
@Component
public class DuplicateRequestAspect {
    private final Set<String> requestSet = Collections.synchronizedSet(new HashSet<>());

    @Around("execution(public * com.smeem.http..*(..))")
    public Object duplicateRequestCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        val request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        val httpMethod = request.getMethod();

        // GET 요청일 경우 중복 체크 생략
        if ("GET".equalsIgnoreCase(httpMethod)) {
            return joinPoint.proceed();
        }

        val requestId = joinPoint.getSignature().toLongString();
        if (requestSet.contains(requestId)) {
            // 중복 요청인 경우
            return handleDuplicateRequest();
        }
        requestSet.add(requestId);

        try {
            return joinPoint.proceed();
        } finally { // 요청 후 요청값 삭제
            requestSet.remove(requestId);
        }
    }

    private ResponseEntity<ExceptionResponse> handleDuplicateRequest() {
        return ResponseEntity
                .status(ExceptionCode.TOO_MANY_REQUESTS.getStatusCode())
                .body(ExceptionResponse.of(ExceptionCode.TOO_MANY_REQUESTS.getMessage() + ": 중복된 요청"));
    }
}
