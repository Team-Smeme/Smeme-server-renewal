package com.smeem.http.controller;

import com.smeem.application.domain.generic.SmeemMessage;
import com.smeem.application.port.input.MemberUseCase;
import com.smeem.application.port.input.dto.request.plan.UpdateMemberPlanRequest;
import com.smeem.application.port.input.dto.response.SmeemResponse;
import com.smeem.application.port.input.dto.response.plan.RetrieveMemberPlanResponse;
import com.smeem.common.util.SmeemConverter;
import com.smeem.http.controller.docs.MemberPlanApiDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/members/plan")
public class MemberPlanApi implements MemberPlanApiDocs {
    private final MemberUseCase memberUseCase;
    private final SmeemConverter smeemConverter;

    @PatchMapping
    public SmeemResponse<?> updatePlan(Principal principal, UpdateMemberPlanRequest request) {
        long memberId = smeemConverter.toMemberId(principal);
        memberUseCase.updatePlan(memberId, request);
        return SmeemResponse.of(SmeemMessage.UPDATE_MEMBER);
    }

    @GetMapping
    public SmeemResponse<RetrieveMemberPlanResponse> retrievePlan(Principal principal) {
        long memberId = smeemConverter.toMemberId(principal);
        return SmeemResponse.of(
                memberUseCase.retrieveMemberPlan(memberId),
                SmeemMessage.RETRIEVE_MEMBER);
    }
}
