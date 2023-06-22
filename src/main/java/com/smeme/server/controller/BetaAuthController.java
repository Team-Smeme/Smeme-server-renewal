package com.smeme.server.controller;

import com.smeme.server.service.auth.BetaAuthService;
import com.smeme.server.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.smeme.server.util.message.ResponseMessage.*;

/*
베타 테스트에 진행할 임시 토큰 발급 API를 구현하는 class입니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/beta")
public class BetaAuthController {

    private final BetaAuthService betaAuthService;

    @PostMapping("/token")
    public ResponseEntity<ApiResponse> getToken() {
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_BETA_AUTH_TOKEN.getMessage(), betaAuthService.createBetaMember()));
    }
}