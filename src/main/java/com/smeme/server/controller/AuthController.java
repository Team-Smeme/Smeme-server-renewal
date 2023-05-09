package com.smeme.server.controller;


import com.smeme.server.dto.auth.SignInRequestDTO;
import com.smeme.server.dto.auth.SignInResponseDTO;
import com.smeme.server.dto.auth.token.TokenResponseDTO;
import com.smeme.server.service.auth.AuthService;
import com.smeme.server.util.ApiResponse;
import com.smeme.server.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.smeme.server.util.message.ErrorMessage.*;
import static com.smeme.server.util.message.ResponseMessage.*;
import static java.util.Objects.isNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping()
    public ResponseEntity<ApiResponse> signIn(
            @RequestHeader("Authorization") String socialAccessToken,
            @RequestBody SignInRequestDTO requestDTO
            ) {
        SignInResponseDTO response = authService.signIn(socialAccessToken, requestDTO);
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_SIGNIN.getMessage(),response));
    }

    @PostMapping("/token")
    public ResponseEntity<ApiResponse> reissueToken(
        Principal principal
    ) {
        TokenResponseDTO response = authService.issueToken(getMemberId(principal));
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_ISSUE_TOKEN.getMessage(),response));
    }

    @PostMapping("/sign-out")
    public ResponseEntity<ApiResponse> signOut(
            Principal principal
    ) {
        authService.signOut(Util.getMemberId(principal));
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_SIGNOUT.getMessage()));
    }

    private Long getMemberId(Principal principal) {
        if (isNull(principal)) {
            throw new SecurityException(EMPTY_REFRESH_TOKEN.getMessage());
        }
        return Long.valueOf(principal.getName());
    }
}
