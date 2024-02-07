package com.smeem.api.test;

import java.security.Principal;

import com.smeem.api.common.ApiResponseUtil;
import com.smeem.api.common.BaseResponse;
import com.smeem.common.config.ValueConfig;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

import static com.smeem.common.code.success.TestSuccessCode.SUCCESS_SEND_PUSH_ALARM;
import static com.smeem.common.code.success.TestSuccessCode.SUCCESS_SERVER_CONNECT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/test")
public class TestController {

    private final TestService testService;
    private final ValueConfig valueConfig;

    @GetMapping
    public ResponseEntity<BaseResponse<?>> test() {
        return ApiResponseUtil.success(SUCCESS_SERVER_CONNECT);
    }

    @GetMapping("/alarm")
    public ResponseEntity<BaseResponse<?>> alarmTest(@Parameter(hidden = true) Principal principal) {
        val title = valueConfig.getMESSAGE_TITLE();
        val body = valueConfig.getMESSAGE_BODY();
         testService.pushTest(title, body, Long.valueOf(principal.getName()));
        return ApiResponseUtil.success(SUCCESS_SEND_PUSH_ALARM);
    }
}