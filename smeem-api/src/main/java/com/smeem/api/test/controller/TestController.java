package com.smeem.api.test.controller;

import java.security.Principal;

import com.smeem.api.common.ApiResponseUtil;
import com.smeem.api.common.BaseResponse;
import com.smeem.api.test.dto.request.TestPushAlarmRequest;
import com.smeem.api.test.service.TestService;
import com.smeem.common.util.Util;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import static com.smeem.common.code.success.TestSuccessCode.SUCCESS_SEND_PUSH_ALARM;
import static com.smeem.common.code.success.TestSuccessCode.SUCCESS_SERVER_CONNECT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/test")
public class TestController {

    private final TestService testService;

    @GetMapping
    public ResponseEntity<BaseResponse<?>> test() {
        return ApiResponseUtil.success(SUCCESS_SERVER_CONNECT);
    }

    @GetMapping("/alarm")
    public ResponseEntity<BaseResponse<?>> alarmTest(Principal principal) {
        val memberId = Util.getMemberId(principal);
        val serviceRequest = TestPushAlarmRequest.of(memberId);
        testService.pushTest(serviceRequest);
        return ApiResponseUtil.success(SUCCESS_SEND_PUSH_ALARM);
    }
}
