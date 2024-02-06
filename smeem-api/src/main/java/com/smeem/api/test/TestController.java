package com.smeem.api.test;



import java.security.Principal;


import com.smeem.api.common.ApiResponse;
import com.smeem.common.config.ValueConfig;
import com.smeem.external.firebase.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/test")
public class TestController {

    private final MessageService messageService;
    private final ValueConfig valueConfig;

    @GetMapping
    public ResponseEntity<ApiResponse> test() {
        return ResponseEntity.ok(ApiResponse.success("server connect"));
    }

    @GetMapping("/alarm")
    public ResponseEntity<ApiResponse> alarmTest(@Parameter(hidden = true) Principal principal) {
        // messageService.pushTest(valueConfig.getMESSAGE_TITLE(), valueConfig.getMESSAGE_BODY(), Long.valueOf(principal.getName()));
        return ResponseEntity.ok(ApiResponse.success("푸시 알림 전송 성공"));
    }
}
