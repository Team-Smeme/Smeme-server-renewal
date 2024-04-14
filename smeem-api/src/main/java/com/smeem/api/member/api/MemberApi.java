package com.smeem.api.member.api;

import com.smeem.api.common.FailureResponse;
import com.smeem.api.common.SuccessResponse;
import com.smeem.api.member.api.dto.request.MemberPlanUpdateRequest;
import com.smeem.api.member.api.dto.request.MemberPushUpdateRequest;
import com.smeem.api.member.api.dto.request.MemberUpdateRequest;
import com.smeem.api.member.api.dto.response.MemberGetResponse;
import com.smeem.api.member.api.dto.response.MemberNameResponse;
import com.smeem.api.member.api.dto.response.MemberPerformanceGetResponse;
import com.smeem.api.member.api.dto.response.MemberUpdateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

@Tag(name = "[Member] 사용자 관련 API (V2)")
public interface MemberApi {
    @Operation(summary = "사용자 프로필 업데이트 API")
    @Parameter(name = "Authorization", description = "Bearer ${Smeme Access Token}", in = HEADER, required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 조회 성공"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰입니다", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "4xx", description = "유효하지 않은 요청", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = FailureResponse.class)))
    })
    ResponseEntity<SuccessResponse<MemberUpdateResponse>> updateProfile(Principal principal, @RequestBody MemberUpdateRequest request);


    @Operation(summary = "사용자 프로필 조회 API")
    @Parameter(name = "Authorization", description = "Bearer ${Smeme Access Token}", in = HEADER, required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 조회 성공"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰입니다", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류", content = @Content(schema = @Schema(implementation = FailureResponse.class)))
    })
    ResponseEntity<SuccessResponse<MemberGetResponse>> getProfile(Principal principal);

    @Operation(summary = "사용자 학습 계획 수정 API")
    @Parameter(name = "Authorization", description = "Bearer ${Smeme Access Token}", in = HEADER, required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 학습 계획 업데이트 성공"),
            @ApiResponse(responseCode = "4xx", description = "유효하지 않은 요청"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰입니다", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류", content = @Content(schema = @Schema(implementation = FailureResponse.class)))
    })
    ResponseEntity<SuccessResponse<?>> updateUserPlan(Principal principal, @Valid @RequestBody MemberPlanUpdateRequest request);

    @Operation(summary = "사용자 닉네임 중복체크 수정 API")
    @Parameter(name = "Authorization", description = "Bearer ${Smeme Access Token}", in = HEADER, required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "닉네임 중복 검사 성공"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰입니다", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류", content = @Content(schema = @Schema(implementation = FailureResponse.class)))
    })
    ResponseEntity<SuccessResponse<MemberNameResponse>> checkDuplicatedName(@Parameter(description = "유저 닉네임") @RequestParam String name);

    @Operation(summary = "사용자 학습 계획 수정 API")
    @Parameter(name = "Authorization", description = "Bearer ${Smeme Access Token}", in = HEADER, required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 푸시알람 동의여부 업데이트 성공"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰입니다", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류", content = @Content(schema = @Schema(implementation = FailureResponse.class)))
    })
    ResponseEntity<SuccessResponse<?>> updateUserPush(Principal principal, @RequestBody MemberPushUpdateRequest request);

    @Operation(summary = "사용자 성과 요약 조회 API")
    @Parameter(name = "Authorization", description = "Bearer ${Smeme Access Token}", in = HEADER, required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공"),
            @ApiResponse(
                    responseCode = "401",
                    description = "유효하지 않은 토큰",
                    content = @Content(schema = @Schema(implementation = FailureResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content(schema = @Schema(implementation = FailureResponse.class))
            )
    })
    ResponseEntity<SuccessResponse<MemberPerformanceGetResponse>> getPerformanceSummary(
            @Parameter(hidden = true) Principal principal
    );
}
