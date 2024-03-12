package com.smeem.api.auth.controller;

import com.smeem.api.auth.controller.dto.request.SignInRequest;
import com.smeem.api.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.security.Principal;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

@Tag(name = "[Auth] 인증 관련 API (V2)")
public interface AuthApi {

    @Operation(summary = "소셜 로그인 API")
    @Parameter(name = "Authorization", description = "Social Platform Access Token", in = HEADER, required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "소셜로그인 성공"),
            @ApiResponse(responseCode = "4xx", description = "유효하지 않은 요청"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰입니다"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    ResponseEntity<BaseResponse<?>> signIn(@RequestHeader("Authorization") final String socialAccessToken, @RequestBody SignInRequest request);

    @Operation(summary = "토큰 재발급 API")
    @Parameter(name = "Authorization", description = "Smeem Refresh Token", in = HEADER, required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 재발급 성공"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰입니다"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    ResponseEntity<BaseResponse<?>> reissueToken(Principal principal);


    @Operation(summary = "사용자 로그아웃 API")
    @Parameter(name = "Authorization", description = "Smeme Access Token", in = HEADER, required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰입니다"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    ResponseEntity<BaseResponse<?>> signOut(Principal principal);


    @Operation(summary = "회원 탈퇴 API")
    @Parameter(name = "Authorization", description = "Smeme Access Token", in = HEADER, required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰입니다"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    ResponseEntity<BaseResponse<?>> withDrawl(Principal principal);

}
