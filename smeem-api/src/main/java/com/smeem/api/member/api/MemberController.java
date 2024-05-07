package com.smeem.api.member.api;

import com.smeem.api.member.api.dto.response.MemberPerformanceGetResponse;
import com.smeem.api.member.api.dto.response.*;
import com.smeem.api.member.service.dto.request.*;
import com.smeem.api.support.ApiResponseGenerator;
import com.smeem.api.common.SuccessResponse;
import com.smeem.api.member.api.dto.request.MemberPlanUpdateRequest;
import com.smeem.api.member.api.dto.request.MemberPushUpdateRequest;
import com.smeem.api.member.api.dto.request.MemberUpdateRequest;
import com.smeem.api.member.service.MemberService;
import com.smeem.api.support.PrincipalConverter;
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
    public ResponseEntity<SuccessResponse<MemberUpdateResponse>> updateProfile(Principal principal, @RequestBody MemberUpdateRequest request) {
        val memberId = PrincipalConverter.getMemberId(principal);
        val response = MemberUpdateResponse.from(memberService.updateUserProfile(MemberServiceUpdateUserProfileRequest.of(memberId, request)));
        return ApiResponseGenerator.success(SUCCESS_UPDATE_USERNAME, response);
    }

    @Override
    @GetMapping("/me")
    public ResponseEntity<SuccessResponse<MemberGetResponse>> getProfile(Principal principal) {
        long memberId = PrincipalConverter.getMemberId(principal);
        val response = MemberGetResponse.from(memberService.getMemberProfile(memberId));
        return ApiResponseGenerator.success(SUCCESS_GET_USER, response);
    }

    @Override
    @PatchMapping("/plan")
    public ResponseEntity<SuccessResponse<?>> updateMemberPlan(
            Principal principal,
            @Valid @RequestBody MemberPlanUpdateRequest request
    ) {
        val memberId = PrincipalConverter.getMemberId(principal);
        memberService.updateLearningPlan(MemberUpdatePlanServiceRequest.of(memberId, request));
        return ApiResponseGenerator.success(SUCCESS_UPDATE_USER_PLAN);
    }

    @Override
    @GetMapping("/nickname/check")
    public ResponseEntity<SuccessResponse<MemberNameResponse>> checkDuplicatedName(@Parameter(description = "유저 닉네임", required = true) @RequestParam String name) {
        val response = MemberNameResponse.from(memberService.checkDuplicatedName(name));
        return ApiResponseGenerator.success(SUCCESS_CHECK_DUPLICATED_NAME, response);
    }

    @Override
    @PatchMapping("/push")
    public ResponseEntity<SuccessResponse<?>> updateUserPush(Principal principal, @RequestBody MemberPushUpdateRequest request) {
        val memberId = PrincipalConverter.getMemberId(principal);
        memberService.updateHasAlarm(MemberPushUpdateServiceRequest.of(memberId, request));
        return ApiResponseGenerator.success(SUCCESS_UPDATE_USER_PUSH);
    }

    @Override
    @GetMapping("/performance/summary")
    public ResponseEntity<SuccessResponse<MemberPerformanceGetResponse>> getPerformanceSummary(Principal principal) {
        val memberId = PrincipalConverter.getMemberId(principal);
        val response = MemberPerformanceGetResponse.from(
                memberService.getPerformanceSummary(MemberPerformanceGetServiceRequest.of(memberId)));
        return ApiResponseGenerator.success(SUCCESS_GET_PERFORMANCE_SUMMARY, response);
    }

    @Override
    @PatchMapping("/visit")
    public ResponseEntity<SuccessResponse<?>> updateMemberVisit(Principal principal) {
        val memberId = PrincipalConverter.getMemberId(principal);
        memberService.updateMemberVisit(MemberVisitUpdateRequest.of(memberId));
        return ApiResponseGenerator.success(SUCCESS_UPDATE_VISIT_TODAY);
    }

    @Override
    @GetMapping("/plan")
    public ResponseEntity<SuccessResponse<MemberPlanGetResponse>> getMemberPlan(Principal principal) {
        val memberId = PrincipalConverter.getMemberId(principal);
        val response = memberService.getMemberPlan(MemberPlanGetServiceRequest.of(memberId))
                .map(MemberPlanGetResponse::from)
                .orElse(null);
        return ApiResponseGenerator.success(SUCCESS_GET_PLAN, response);
    }

}
