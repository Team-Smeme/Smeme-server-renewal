package com.smeme.server.controller;

import com.smeme.server.dto.auth.beta.BetaSignInRequestDTO;
import com.smeme.server.dto.auth.beta.BetaTokenResponseDTO;
import com.smeme.server.service.auth.BetaAuthService;
import com.smeme.server.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.smeme.server.util.message.ResponseMessage.*;

/*
베타 테스트에 진행할 임시 토큰 발급 API를 구현하는 class입니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/beta")
@Tag(name = "BetaAuth", description = "베타 테스트용 인증 API")
@Deprecated
public class BetaAuthController {

    private final BetaAuthService betaAuthService;

    @Operation(summary = "베타 테스트용 임시 토큰 발급", description = "베타 테스트용 임시 토큰을 발급합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "베타 테스트용 임시 토큰 발급 성공"
                    , content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = BetaTokenResponseDTO.class)))})
    @PostMapping("/token")
    public ResponseEntity<ApiResponse> getToken(@RequestBody BetaSignInRequestDTO request) {
        BetaTokenResponseDTO response = betaAuthService.createBetaMember(request);
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_BETA_AUTH_TOKEN.getMessage(), response));
    }
}
