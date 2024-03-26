package com.smeem.api.test.api;

import java.security.Principal;

import com.smeem.api.support.ApiResponseGenerator;
import com.smeem.api.common.SuccessResponse;
import com.smeem.api.support.PrincipalConverter;
import com.smeem.api.test.service.TestService;
import com.smeem.api.test.service.dto.request.TestPushAlarmServiceRequest;
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
public class TestApiController implements TestApi {

    private final TestService testService;

    @GetMapping
    public ResponseEntity<SuccessResponse<?>> connect() {
        return ApiResponseGenerator.success(SUCCESS_SERVER_CONNECT);
    }

    @GetMapping("/alarm")
    public ResponseEntity<SuccessResponse<?>> sendMessage(Principal principal) {
        val memberId = PrincipalConverter.getMemberId(principal);
        testService.sendMessage(TestPushAlarmServiceRequest.of(memberId));
        return ApiResponseGenerator.success(SUCCESS_SEND_PUSH_ALARM);
    }
}
