package com.smeem.api.auth.controller;

import com.smeem.api.auth.controller.dto.request.SignInRequest;
import com.smeem.api.auth.controller.dto.response.SignInResponse;
import com.smeem.api.auth.controller.dto.response.token.TokenResponse;
import com.smeem.api.auth.service.AuthService;
import com.smeem.api.auth.service.TokenService;
import com.smeem.api.auth.service.dto.request.SignInServiceRequest;
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
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<BaseResponse<?>> signIn(@RequestHeader("Authorization") String socialAccessToken,
                                                  @RequestBody SignInRequest request) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SignInResponse response = SignInResponse.of(authService.signIn(socialAccessToken, SignInServiceRequest.of(request)));
        return ApiResponseUtil.success(SUCCESS_SIGNIN, response);
    }

    @PostMapping("/token")
    public ResponseEntity<BaseResponse<?>> reissueToken(Principal principal) {
        TokenResponse response = TokenResponse.of(tokenService.issueToken(Util.getMemberId(principal)));
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
