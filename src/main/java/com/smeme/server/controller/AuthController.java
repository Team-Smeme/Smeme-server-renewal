package com.smeme.server.controller;

import com.smeme.server.dto.auth.SignInRequestDTO;
import com.smeme.server.dto.auth.SignInResponseDTO;
import com.smeme.server.dto.auth.token.TokenResponseDTO;
import com.smeme.server.service.auth.AuthService;
import com.smeme.server.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;

import static com.smeme.server.util.ApiResponse.success;
import static com.smeme.server.util.Util.getMemberId;
import static com.smeme.server.util.message.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<ApiResponse> signIn(@RequestHeader("Authorization") String socialAccessToken, @RequestBody SignInRequestDTO request
    ) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SignInResponseDTO response = authService.signIn(socialAccessToken, request);
        return ResponseEntity.ok(success(SUCCESS_SIGNIN.getMessage(), response));
    }

    @PostMapping("/token")
    public ResponseEntity<ApiResponse> reissueToken(Principal principal) {
        TokenResponseDTO response = authService.issueToken(getMemberId(principal));
        return ResponseEntity.ok(success(SUCCESS_ISSUE_TOKEN.getMessage(), response));
    }

    @PostMapping("/sign-out")
    public ResponseEntity<ApiResponse> signOut(Principal principal) {
        authService.signOut(getMemberId(principal));
        return ResponseEntity.ok(success(SUCCESS_SIGNOUT.getMessage()));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> withDrawl(Principal principal) {
        authService.withdraw(getMemberId(principal));
        return ResponseEntity.ok(success(SUCCESS_WITHDRAW.getMessage()));
    }
}
