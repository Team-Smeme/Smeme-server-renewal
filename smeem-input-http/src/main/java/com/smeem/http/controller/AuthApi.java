package com.smeem.http.controller;

import com.smeem.application.domain.generic.SmeemMessage;
import com.smeem.application.port.input.AuthUseCase;
import com.smeem.application.port.input.dto.request.member.WithdrawRequest;
import com.smeem.http.controller.docs.AuthApiDocs;
import com.smeem.application.port.input.dto.request.auth.SignInRequest;
import com.smeem.http.controller.dto.SmeemResponse;
import com.smeem.application.port.input.dto.response.auth.GenerateTokenResponse;
import com.smeem.application.port.input.dto.response.auth.SignInResponse;
import com.smeem.common.util.SmeemConverter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/auth")
public class AuthApi implements AuthApiDocs {
    private final AuthUseCase authUseCase;
    private final SmeemConverter smeemConverter;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SmeemResponse<SignInResponse> signIn(
            @RequestHeader("Authorization") String socialAccessToken,
            @RequestBody SignInRequest request
    ) {
        return SmeemResponse.of(authUseCase.signIn(socialAccessToken, request), SmeemMessage.SIGNED_IN);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/token")
    public SmeemResponse<GenerateTokenResponse> generateToken(Principal principal) {
        val memberId = smeemConverter.toMemberId(principal);
        return SmeemResponse.of(authUseCase.generateToken(memberId), SmeemMessage.GENERATE_TOKEN);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/sign-out")
    public SmeemResponse<?> signOut(Principal principal) {
        val memberId = smeemConverter.toMemberId(principal);
        authUseCase.signOut(memberId);
        return SmeemResponse.of(SmeemMessage.SIGNED_OUT);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public SmeemResponse<?> withdraw(Principal principal, @RequestBody WithdrawRequest request) {
        val memberId = smeemConverter.toMemberId(principal);
        authUseCase.withdraw(memberId, request);
        return SmeemResponse.of(SmeemMessage.WITHDRAW);
    }
}
