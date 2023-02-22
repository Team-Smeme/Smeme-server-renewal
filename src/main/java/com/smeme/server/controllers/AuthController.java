package com.smeme.server.controllers;


import com.smeme.server.dtos.auth.AuthSignInRequestDto;
import com.smeme.server.dtos.auth.AuthSignInResponseDto;
import com.smeme.server.services.AuthService;
import com.smeme.server.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping()
    public ResponseEntity<ApiResponse> signIn(
            @RequestHeader(value = "Authorization") String clientId,
            @RequestBody AuthSignInRequestDto requestDto
    ) {

       AuthSignInResponseDto responseDto = authService.signIn(requestDto, clientId);

        ApiResponse apiResponse = ApiResponse.of(
                HttpStatus.OK.value(), true, "소셜 로그인성공", responseDto
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
