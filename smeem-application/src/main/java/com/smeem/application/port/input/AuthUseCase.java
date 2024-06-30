package com.smeem.application.port.input;

import com.smeem.application.port.input.dto.request.auth.SignInRequest;
import com.smeem.application.port.input.dto.response.auth.GenerateTokenResponse;
import com.smeem.application.port.input.dto.response.auth.SignInResponse;

public interface AuthUseCase {
    SignInResponse signIn(String socialAccessToken, SignInRequest request);
    GenerateTokenResponse generateToken(long memberId);
    void signOut(long memberId);
    void withdraw(long memberId);
}
