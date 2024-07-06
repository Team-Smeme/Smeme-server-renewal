package com.smeem.http.controller.docs;

import com.smeem.application.port.input.dto.request.auth.SignInRequest;
import com.smeem.http.controller.dto.SmeemResponse;
import com.smeem.application.port.input.dto.response.auth.GenerateTokenResponse;
import com.smeem.application.port.input.dto.response.auth.SignInResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.security.Principal;

@Tag(name = "AuthApi", description = "인증(Auth) 관련 Api 입니다.")
public interface AuthApiDocs {

    @Operation(summary = "로그인 api", description = "회원가입 또는 로그인합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "CREATED success",
                    content = @Content(schema = @Schema(implementation = SignInResponse.class)))
    })
    SmeemResponse<SignInResponse> signIn(
            @Parameter(
                    name = "Authorization",
                    description = "소셜서비스 액세스 토큰",
                    required = true,
                    in = ParameterIn.HEADER
            ) String socialAccessToken,
            @RequestBody(
                    description = "로그인 Request Body",
                    required = true,
                    content = @Content(schema = @Schema(implementation = SignInRequest.class))
            ) SignInRequest request
    );

    @Operation(summary = "토큰 발급 api", description = "액세스 토큰을 발급합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK success",
                    content = @Content(schema = @Schema(implementation = GenerateTokenResponse.class))
            )
    })
    SmeemResponse<GenerateTokenResponse> generateToken(@Parameter(hidden = true) Principal principal);

    @Operation(summary = "로그아웃 api", description = "회원이 로그아웃합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "NO CONTENT success"
            )
    })
    SmeemResponse<?> signOut(
            @Parameter(hidden = true) Principal principal
    );

    @Operation(summary = "탈퇴 api", description = "회원이 탈퇴합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "NO CONTENT success"
            )
    })
    SmeemResponse<?> withdraw(@Parameter(hidden = true) Principal principal);
}
