package com.smeme.server.controller;

import com.smeme.server.dto.badge.BadgeListResponseDTO;
import com.smeme.server.dto.member.*;
import com.smeme.server.service.MemberService;
import com.smeme.server.util.ApiResponse;
import com.smeme.server.util.Util;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.smeme.server.util.message.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/members")
@Tag(name = "Member", description = "사용자 관련 API")
@SecurityRequirement(name = "Authorization")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "사용자 정보 수정", description = "사용자의 정보를 수정합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "사용자 정보 수정 성공")})
    @PatchMapping()
    public ResponseEntity<ApiResponse> updateUserProfile(
            Principal principal, @RequestBody MemberUpdateRequestDTO requestDTO) {
        MemberUpdateResponseDTO response = memberService.updateMember(Util.getMemberId(principal), requestDTO);
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_UPDATE_USERNAME.getMessage(), response));
    }

    @Operation(summary = "사용자 정보 조회", description = "사용자의 정보를 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "사용자 정보 수정 성공"
            ,content = @Content(schema = @Schema(implementation = MemberGetResponseDTO.class)))})
    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getUserProfile(Principal principal) {
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_GET_USER.getMessage(),memberService.getMember(Util.getMemberId(principal))));
    }

    @Operation(summary = "사용자 학습 계획 변경", description = "사용자의 학습 계획을 변경합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "사용자 학습 계획 성공")})
    @PatchMapping("/plan")
    public ResponseEntity<ApiResponse> updateUserPlan(Principal principal, @Valid @RequestBody MemberPlanUpdateRequestDTO requestDTO) {
        memberService.updateMemberPlan(Util.getMemberId(principal), requestDTO);
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_UPDATE_USER_PLAN.getMessage()));
    }

    @Operation(summary = "닉네임 중복 체크", description = "닉네임 중복 체크를 합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "닉네임 중복 체크 성공"
                    ,content = @Content(schema = @Schema(implementation = MemberNameResponseDTO.class)))})
    @GetMapping("/nickname/check")
    public ResponseEntity<ApiResponse> checkDuplicatedName(@Parameter(description = "유저 닉네임") @RequestParam String name) {
        MemberNameResponseDTO response = memberService.checkDuplicatedName(name);
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_CHECK_DUPLICATED_NAME.getMessage(), response));
    }

    @Operation(summary = "사용자 푸쉬 알람 동의 여부 수정", description = "사용자의 푸쉬 알람 동의 여부를 수정합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "사용자 푸쉬 알람 동의 여부 수정 성공")})
    @PatchMapping("/push")
    public ResponseEntity<ApiResponse> updateUserPush(Principal principal, @RequestBody MemberPushUpdateRequestDTO requestDTO) {
        memberService.updateMemberPush(Util.getMemberId(principal), requestDTO);
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_UPDATE_USER_PUSH.getMessage()));
    }
    
}
