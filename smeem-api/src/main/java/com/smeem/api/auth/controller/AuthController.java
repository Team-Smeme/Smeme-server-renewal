package com.smeem.api.auth.controller;

import com.smeem.api.auth.controller.dto.request.SignInRequestDTO;
import com.smeem.api.auth.controller.dto.response.SignInResponseDTO;
import com.smeem.api.auth.controller.dto.response.token.TokenResponseDTO;
import com.smeem.api.auth.service.AuthService;
import com.smeem.api.common.ApiResponseUtil;
import com.smeem.api.common.BaseResponse;
import com.smeem.common.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;

import static com.smeem.common.code.success.AuthSuccessCode.*;
import static com.smeem.common.code.success.TokenSuccessCode.SUCCESS_ISSUE_TOKEN;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<BaseResponse<?>> signIn(@RequestHeader("Authorization") String socialAccessToken, @RequestBody SignInRequestDTO request
    ) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SignInResponseDTO response = authService.signIn(socialAccessToken, request);
        return ApiResponseUtil.success(SUCCESS_SIGNIN, response);
    }

    @PostMapping("/token")
    public ResponseEntity<BaseResponse<?>> reissueToken(Principal principal) {
        TokenResponseDTO response = authService.issueToken(Util.getMemberId(principal));
        return ApiResponseUtil.success(SUCCESS_ISSUE_TOKEN, response);
    }

    @PostMapping("/sign-out")
    public ResponseEntity<BaseResponse<?>> signOut(Principal principal) {
        authService.signOut(Util.getMemberId(principal));
        return ApiResponseUtil.success(SUCCESS_SIGNOUT);
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<?>> withDrawl(Principal principal) {
        authService.withdraw(Util.getMemberId(principal));
        return ApiResponseUtil.success(SUCCESS_WITHDRAW);
    }
}
