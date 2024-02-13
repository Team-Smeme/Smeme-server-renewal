package com.smeem.api.member.controller;

import com.smeem.api.common.ApiResponseUtil;
import com.smeem.api.common.BaseResponse;
import com.smeem.api.member.controller.dto.request.MemberPlanUpdateRequest;
import com.smeem.api.member.controller.dto.request.MemberPushUpdateRequest;
import com.smeem.api.member.controller.dto.request.MemberUpdateRequest;
import com.smeem.api.member.controller.dto.response.MemberNameResponse;
import com.smeem.api.member.service.MemberService;
import com.smeem.api.member.service.dto.request.MemberPushUpdateServiceRequest;
import com.smeem.api.member.service.dto.request.MemberUpdatePlanServiceRequest;
import com.smeem.api.member.service.dto.request.MemberServiceUpdateUserProfileRequest;
import com.smeem.common.util.Util;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
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
    public ResponseEntity<BaseResponse<?>> updateProfile(Principal principal, @RequestBody MemberUpdateRequest request) {
        val response = memberService.updateUserProfile(
                Util.getMemberId(principal),
                MemberServiceUpdateUserProfileRequest.of(request));
        return ApiResponseUtil.success(SUCCESS_UPDATE_USERNAME, response);
    }

    @GetMapping("/me")
    public ResponseEntity<BaseResponse<?>> getProfile(Principal principal) {
        val response = memberService.getMemberProfile(Util.getMemberId(principal));
        return ApiResponseUtil.success(SUCCESS_GET_USER, response);
    }

    @PatchMapping("/plan")
    public ResponseEntity<BaseResponse<?>> updateUserPlan(Principal principal, @Valid @RequestBody MemberPlanUpdateRequest request) {
        memberService.updateLearningPlan(Util.getMemberId(principal), MemberUpdatePlanServiceRequest.of(request));
        return ApiResponseUtil.success(SUCCESS_UPDATE_USER_PLAN);
    }

    @GetMapping("/nickname/check")
    public ResponseEntity<BaseResponse<?>> checkDuplicatedName(@Parameter(description = "유저 닉네임") @RequestParam String name) {
        val response = MemberNameResponse.of(memberService.checkDuplicatedName(name));
        return ApiResponseUtil.success(SUCCESS_CHECK_DUPLICATED_NAME, response);
    }

    @PatchMapping("/push")
    public ResponseEntity<BaseResponse<?>> updateUserPush(Principal principal, @RequestBody MemberPushUpdateRequest request) {
        memberService.updateHasAlarm(Util.getMemberId(principal), MemberPushUpdateServiceRequest.of(request));
        return ApiResponseUtil.success(SUCCESS_UPDATE_USER_PUSH);
    }

}
