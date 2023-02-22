package com.smeme.server.controllers;


import com.smeme.server.dtos.auth.AuthGetTokenResponseDto;
import com.smeme.server.dtos.auth.AuthSignInRequestDto;
import com.smeme.server.dtos.auth.AuthSignInResponseDto;
import com.smeme.server.services.AuthService;
import com.smeme.server.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping()
    public ResponseEntity<ApiResponse> signIn(
            @RequestHeader(value = "authorization") String clientId,
            @RequestBody AuthSignInRequestDto requestDto
    ) {

       AuthSignInResponseDto responseDto = authService.signIn(requestDto, clientId);

        ApiResponse apiResponse = ApiResponse.of(
                HttpStatus.OK.value(), true, "소셜 로그인성공", responseDto
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/token")
    public ResponseEntity<ApiResponse> getToken(Principal principal)
     {
         Long userId = Long.valueOf(principal.getName());

         AuthGetTokenResponseDto responseDto =  authService.getToken(userId);
        ApiResponse apiResponse = ApiResponse.of(
                HttpStatus.OK.value(), true, "토큰 재발급 성공", responseDto
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
