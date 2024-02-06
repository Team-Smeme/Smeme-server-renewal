package com.smeem.api.auth.controller;

import com.smeem.api.auth.controller.dto.request.SignInRequestDTO;
import com.smeem.api.auth.controller.dto.response.SignInResponseDTO;
import com.smeem.api.auth.controller.dto.response.token.TokenResponseDTO;
import com.smeem.api.auth.service.AuthService;
import com.smeem.api.common.ApiResponse;
import com.smeem.common.code.ResponseMessage;
import com.smeem.common.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/com.smeem.auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<ApiResponse> signIn(@RequestHeader("Authorization") String socialAccessToken, @RequestBody SignInRequestDTO request
    ) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SignInResponseDTO response = authService.signIn(socialAccessToken, request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_SIGNIN.getMessage(), response));
    }

    @PostMapping("/token")
    public ResponseEntity<ApiResponse> reissueToken(Principal principal) {
        TokenResponseDTO response = authService.issueToken(Util.getMemberId(principal));
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_ISSUE_TOKEN.getMessage(), response));
    }

    @PostMapping("/sign-out")
    public ResponseEntity<ApiResponse> signOut(Principal principal) {
        authService.signOut(Util.getMemberId(principal));
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_SIGNOUT.getMessage()));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> withDrawl(Principal principal) {
        authService.withdraw(Util.getMemberId(principal));
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_WITHDRAW.getMessage()));
    }
}
