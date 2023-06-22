package com.smeme.server.controller;

import com.smeme.server.dto.member.MemberPlanUpdateRequestDTO;
import com.smeme.server.dto.member.MemberUpdateRequestDTO;
import com.smeme.server.service.MemberService;
import com.smeme.server.util.ApiResponse;
import com.smeme.server.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.smeme.server.util.message.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/members")
public class MemberController {

    private final MemberService memberService;

    @PatchMapping()
    public ResponseEntity<ApiResponse> updateUserProfile(
            Principal principal, @RequestBody MemberUpdateRequestDTO requestDTO) {
        memberService.updateMember(Util.getMemberId(principal), requestDTO);
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_UPDATE_USERNAME.getMessage()));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getUserProfile(Principal principal) {
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_GET_USER.getMessage(),memberService.getMember(Util.getMemberId(principal))));
    }

    @PatchMapping("/plan")
    public ResponseEntity<ApiResponse> updateUserPlan(Principal principal, @RequestBody MemberPlanUpdateRequestDTO requestDTO) {
        memberService.updateMemberPlan(Util.getMemberId(principal), requestDTO);
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_UPDATE_USER_PLAN.getMessage()));
    }

}
