package com.smeem.api.member.controller;

import com.smeem.api.common.ApiResponseUtil;
import com.smeem.api.common.BaseResponse;
import com.smeem.api.member.controller.dto.request.MemberPlanUpdateRequestDTO;
import com.smeem.api.member.controller.dto.request.MemberPushUpdateRequestDTO;
import com.smeem.api.member.controller.dto.request.MemberUpdateRequestDTO;
import com.smeem.api.member.controller.dto.response.MemberGetResponseDTO;
import com.smeem.api.member.controller.dto.response.MemberNameResponseDTO;
import com.smeem.api.member.controller.dto.response.MemberUpdateResponseDTO;
import com.smeem.api.member.service.MemberService;
import com.smeem.common.util.Util;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.smeem.common.code.success.MemberSuccessCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/members")
public class MemberController {

    private final MemberService memberService;

    @PatchMapping
    public ResponseEntity<BaseResponse<?>> updateProfile(Principal principal, @RequestBody MemberUpdateRequestDTO request) {
        MemberUpdateResponseDTO response = memberService.updateMember(Util.getMemberId(principal), request);
        return ApiResponseUtil.success(SUCCESS_UPDATE_USERNAME, response);
    }

    @GetMapping("/me")
    public ResponseEntity<BaseResponse<?>> getProfile(Principal principal) {
        MemberGetResponseDTO response = memberService.getProfile(Util.getMemberId(principal));
        return ApiResponseUtil.success(SUCCESS_GET_USER, response);
    }

    @PatchMapping("/plan")
    public ResponseEntity<BaseResponse<?>> updateUserPlan(Principal principal, @Valid @RequestBody MemberPlanUpdateRequestDTO request) {
        memberService.updateMemberPlan(Util.getMemberId(principal), request);
        return ApiResponseUtil.success(SUCCESS_UPDATE_USER_PLAN);
    }

    @GetMapping("/nickname/check")
    public ResponseEntity<BaseResponse<?>> checkDuplicatedName(@Parameter(description = "유저 닉네임") @RequestParam String name) {
        MemberNameResponseDTO response = memberService.checkDuplicatedName(name);
        return ApiResponseUtil.success(SUCCESS_CHECK_DUPLICATED_NAME, response);
    }

    @PatchMapping("/push")
    public ResponseEntity<BaseResponse<?>> updateUserPush(Principal principal, @RequestBody MemberPushUpdateRequestDTO request) {
        memberService.updateHasAlarm(Util.getMemberId(principal), request);
        return ApiResponseUtil.success(SUCCESS_UPDATE_USER_PUSH);
    }

}
