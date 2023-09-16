package com.smeme.server.controller;

import static com.smeme.server.util.ApiResponse.*;

import java.security.Principal;

import com.smeme.server.config.ValueConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smeme.server.service.MessageService;
import com.smeme.server.util.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Test", description = "테스트 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/test")
public class TestController {

    private final MessageService messageService;
    private final ValueConfig valueConfig;

    @Operation(summary = "서버 연결 테스트", description = "서버 연결을 테스트합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "서버 연결 성공")
    })
    @GetMapping
    public ResponseEntity<ApiResponse> test() {
        return ResponseEntity.ok(success("server connect"));
    }

    @Operation(summary = "푸시 알림 테스트", description = "푸시 알림을 테스트합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "알림 전송 성공")
    })
    @GetMapping("/alarm")
    public ResponseEntity<ApiResponse> alarmTest(@Parameter(hidden = true) Principal principal) {
        messageService.pushTest(valueConfig.getMESSAGE_TITLE(), valueConfig.getMESSAGE_BODY(), Long.valueOf(principal.getName()));
        return ResponseEntity.ok(success("푸시 알림 전송 성공"));
    }
}
