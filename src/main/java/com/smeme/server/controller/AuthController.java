package com.smeme.server.controller;


import com.smeme.server.dto.auth.SignInRequestDTO;
import com.smeme.server.dto.auth.SignInResponseDTO;
import com.smeme.server.dto.auth.token.TokenResponseDTO;
import com.smeme.server.service.auth.AuthService;
import com.smeme.server.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Auth", description = "인증/인가 관련 API")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "소셜 로그인", description = "소셜 로그인을 합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = SignInResponseDTO.class)))})
    @PostMapping
    public ResponseEntity<ApiResponse> signIn(@RequestHeader("Authorization") String socialAccessToken, @RequestBody SignInRequestDTO request) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SignInResponseDTO response = authService.signIn(socialAccessToken, request);
        return ResponseEntity.ok(success(SUCCESS_SIGNIN.getMessage(), response));
    }

    @Operation(summary = "토큰 재발급", description = "토큰을 재발급 받습니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "토큰 재발급 성공",
                    content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = TokenResponseDTO.class)))})
    @PostMapping("/token")
    public ResponseEntity<ApiResponse> reissueToken(Principal principal) {
        TokenResponseDTO response = authService.issueToken(getMemberId(principal));
        return ResponseEntity.ok(success(SUCCESS_ISSUE_TOKEN.getMessage(), response));
    }

    @SecurityRequirement(name = "Authorization")
    @Operation(summary = "로그아웃", description = "로그아웃 합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그아웃 성공",
                    content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ApiResponse.class)))})
    @PostMapping("/sign-out")
    public ResponseEntity<ApiResponse> signOut(Principal principal) {
        authService.signOut(getMemberId(principal));
        return ResponseEntity.ok(success(SUCCESS_SIGNOUT.getMessage()));
    }

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴를 합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "회원 탈퇴 성공",
                    content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ApiResponse.class)))})
    @DeleteMapping
    public ResponseEntity<ApiResponse> withDrawl(Principal principal) {
        authService.withdraw(getMemberId(principal));
        return ResponseEntity.ok(success(SUCCESS_WITHDRAW.getMessage()));
    }
}
