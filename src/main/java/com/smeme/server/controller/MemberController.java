package com.smeme.server.controller;

import com.smeme.server.dto.member.*;
import com.smeme.server.service.MemberService;
import com.smeme.server.util.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.smeme.server.util.ApiResponse.success;
import static com.smeme.server.util.Util.getMemberId;
import static com.smeme.server.util.message.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/members")
public class MemberController {

    private final MemberService memberService;

    @PatchMapping
    public ResponseEntity<ApiResponse> updateProfile(Principal principal, @RequestBody MemberUpdateRequestDTO request) {
        MemberUpdateResponseDTO response = memberService.updateMember(getMemberId(principal), request);
        return ResponseEntity.ok(success(SUCCESS_UPDATE_USERNAME.getMessage(), response));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getProfile(Principal principal) {
        MemberGetResponseDTO response = memberService.getProfile(getMemberId(principal));
        return ResponseEntity.ok(success(SUCCESS_GET_USER.getMessage(), response));
    }

    @PatchMapping("/plan")
    public ResponseEntity<ApiResponse> updateUserPlan(Principal principal, @Valid @RequestBody MemberPlanUpdateRequestDTO request) {
        memberService.updateMemberPlan(getMemberId(principal), request);
        return ResponseEntity.ok(success(SUCCESS_UPDATE_USER_PLAN.getMessage()));
    }

    @GetMapping("/nickname/check")
    public ResponseEntity<ApiResponse> checkDuplicatedName(@Parameter(description = "유저 닉네임") @RequestParam String name) {
        MemberNameResponseDTO response = memberService.checkDuplicatedName(name);
        return ResponseEntity.ok(success(SUCCESS_CHECK_DUPLICATED_NAME.getMessage(), response));
    }

    @PatchMapping("/push")
    public ResponseEntity<ApiResponse> updateUserPush(Principal principal, @RequestBody MemberPushUpdateRequestDTO request) {
        memberService.updateHasAlarm(getMemberId(principal), request);
        return ResponseEntity.ok(success(SUCCESS_UPDATE_USER_PUSH.getMessage()));
    }

}
