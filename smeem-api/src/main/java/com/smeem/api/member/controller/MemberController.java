package com.smeem.api.member.controller;


import com.smeem.api.common.ApiResponse;
import com.smeem.api.member.controller.dto.request.MemberPlanUpdateRequestDTO;
import com.smeem.api.member.controller.dto.request.MemberPushUpdateRequestDTO;
import com.smeem.api.member.controller.dto.request.MemberUpdateRequestDTO;
import com.smeem.api.member.controller.dto.response.MemberGetResponseDTO;
import com.smeem.api.member.controller.dto.response.MemberNameResponseDTO;
import com.smeem.api.member.controller.dto.response.MemberUpdateResponseDTO;
import com.smeem.api.member.service.MemberService;
import com.smeem.common.code.ResponseMessage;
import com.smeem.common.util.Util;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/members")
public class MemberController {

    private final MemberService memberService;

    @PatchMapping
    public ResponseEntity<ApiResponse> updateProfile(Principal principal, @RequestBody MemberUpdateRequestDTO request) {
        MemberUpdateResponseDTO response = memberService.updateMember(Util.getMemberId(principal), request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_UPDATE_USERNAME.getMessage(), response));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getProfile(Principal principal) {
        MemberGetResponseDTO response = memberService.getProfile(Util.getMemberId(principal));
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_USER.getMessage(), response));
    }

    @PatchMapping("/plan")
    public ResponseEntity<ApiResponse> updateUserPlan(Principal principal, @Valid @RequestBody MemberPlanUpdateRequestDTO request) {
        memberService.updateMemberPlan(Util.getMemberId(principal), request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_UPDATE_USER_PLAN.getMessage()));
    }

    @GetMapping("/nickname/check")
    public ResponseEntity<ApiResponse> checkDuplicatedName(@Parameter(description = "유저 닉네임") @RequestParam String name) {
        MemberNameResponseDTO response = memberService.checkDuplicatedName(name);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_CHECK_DUPLICATED_NAME.getMessage(), response));
    }

    @PatchMapping("/push")
    public ResponseEntity<ApiResponse> updateUserPush(Principal principal, @RequestBody MemberPushUpdateRequestDTO request) {
        memberService.updateHasAlarm(Util.getMemberId(principal), request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_UPDATE_USER_PUSH.getMessage()));
    }

}
