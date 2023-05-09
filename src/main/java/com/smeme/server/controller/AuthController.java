package com.smeme.server.controller;


import com.smeme.server.dto.auth.SignInRequestDTO;
import com.smeme.server.dto.auth.SignInResponseDTO;
import com.smeme.server.service.auth.AuthService;
import com.smeme.server.util.ApiResponse;
import com.smeme.server.util.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<ApiResponse> signIn(
            @RequestHeader("Authorization") String socialAccessToken,
            @RequestBody SignInRequestDTO signInRequestDTO
            ) {

        SignInResponseDTO signInResponseDTO = authService.signIn(socialAccessToken, signInRequestDTO);

        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_SIGNIN.getMessage(),signInResponseDTO));
    }

}
