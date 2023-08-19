package com.smeme.server.controller;

import com.smeme.server.dto.member.*;
import com.smeme.server.service.MemberService;
import com.smeme.server.util.ApiResponse;
import com.smeme.server.util.Util;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.smeme.server.util.message.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/members")
@SecurityRequirement(name = "Authorization")
public class MemberController {

    private final MemberService memberService;

    @PatchMapping
    public ResponseEntity<ApiResponse> updateUserProfile(
        Principal principal, @RequestBody MemberUpdateRequestDTO requestDTO
    ) {
        MemberUpdateResponseDTO response = memberService.updateMember(Util.getMemberId(principal), requestDTO);
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_UPDATE_USERNAME.getMessage(), response));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getUserProfile(Principal principal) {
        MemberGetResponseDTO response = memberService.getMember(Util.getMemberId(principal));
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_GET_USER.getMessage(), response));
    }

    @PatchMapping("/plan")
    public ResponseEntity<ApiResponse> updateUserPlan(
        Principal principal, @Valid @RequestBody MemberPlanUpdateRequestDTO requestDTO
    ) {
        memberService.updateMemberPlan(Util.getMemberId(principal), requestDTO);
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_UPDATE_USER_PLAN.getMessage()));
    }

    @GetMapping("/nickname/check")
    public ResponseEntity<ApiResponse> checkDuplicatedName(@RequestParam String name) {
        MemberNameResponseDTO response = memberService.checkDuplicatedName(name);
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_CHECK_DUPLICATED_NAME.getMessage(), response));
    }

}
