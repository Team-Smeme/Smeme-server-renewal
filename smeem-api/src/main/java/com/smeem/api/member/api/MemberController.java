package com.smeem.api.member.api;

import com.smeem.api.common.ApiResponseUtil;
import com.smeem.api.common.dto.SuccessResponse;
import com.smeem.api.member.api.dto.request.MemberPlanUpdateRequest;
import com.smeem.api.member.api.dto.request.MemberPushUpdateRequest;
import com.smeem.api.member.api.dto.request.MemberUpdateRequest;
import com.smeem.api.member.api.dto.response.MemberGetResponse;
import com.smeem.api.member.api.dto.response.MemberNameResponse;
import com.smeem.api.member.api.dto.response.MemberUpdateResponse;
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
public class MemberController implements MemberApi {

    private final MemberService memberService;

    @Override
    @PatchMapping
    public ResponseEntity<SuccessResponse<?>> updateProfile(Principal principal, @RequestBody MemberUpdateRequest request) {
        val response = MemberUpdateResponse.from(memberService.updateUserProfile(
                Util.getMemberId(principal),
                MemberServiceUpdateUserProfileRequest.of(request)));
        return ApiResponseUtil.success(SUCCESS_UPDATE_USERNAME, response);
    }

    @Override
    @GetMapping("/me")
    public ResponseEntity<SuccessResponse<?>> getProfile(Principal principal) {
        val response = MemberGetResponse.from(memberService.getMemberProfile(Util.getMemberId(principal)));
        return ApiResponseUtil.success(SUCCESS_GET_USER, response);
    }

    @Override
    @PatchMapping("/plan")
    public ResponseEntity<SuccessResponse<?>> updateUserPlan(Principal principal, @Valid @RequestBody MemberPlanUpdateRequest request) {
        memberService.updateLearningPlan(Util.getMemberId(principal), MemberUpdatePlanServiceRequest.of(request));
        return ApiResponseUtil.success(SUCCESS_UPDATE_USER_PLAN);
    }

    @Override
    @GetMapping("/nickname/check")
    public ResponseEntity<SuccessResponse<?>> checkDuplicatedName(@Parameter(description = "유저 닉네임", required = true) @RequestParam String name) {
        val response = MemberNameResponse.from(memberService.checkDuplicatedName(name));
        return ApiResponseUtil.success(SUCCESS_CHECK_DUPLICATED_NAME, response);
    }

    @Override
    @PatchMapping("/push")
    public ResponseEntity<SuccessResponse<?>> updateUserPush(Principal principal, @RequestBody MemberPushUpdateRequest request) {
        memberService.updateHasAlarm(Util.getMemberId(principal), MemberPushUpdateServiceRequest.of(request));
        return ApiResponseUtil.success(SUCCESS_UPDATE_USER_PUSH);
    }

}
