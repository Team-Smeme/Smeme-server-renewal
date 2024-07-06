package com.smeem.http.controller;

import com.smeem.application.domain.generic.SmeemMessage;
import com.smeem.application.port.input.MemberUseCase;
import com.smeem.application.port.input.dto.request.member.UpdateMemberHasPushAlarmRequest;
import com.smeem.application.port.input.dto.request.member.UpdateMemberRequest;
import com.smeem.http.controller.dto.SmeemResponse;
import com.smeem.application.port.input.dto.response.member.RetrieveMemberResponse;
import com.smeem.application.port.input.dto.response.member.RetrievePerformanceResponse;
import com.smeem.application.port.input.dto.response.member.UpdateMemberResponse;
import com.smeem.application.port.input.dto.response.member.UsernameDuplicatedResponse;
import com.smeem.common.util.SmeemConverter;
import com.smeem.http.controller.docs.MemberApiDocs;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/members")
public class MemberApi implements MemberApiDocs {
    private final MemberUseCase memberUseCase;
    private final SmeemConverter smeemConverter;

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    public SmeemResponse<UpdateMemberResponse> updateMember(
            Principal principal,
            @RequestBody UpdateMemberRequest request
    ) {
        val memberId = smeemConverter.toMemberId(principal);
        return SmeemResponse.of(
                memberUseCase.updateMember(memberId, request),
                SmeemMessage.UPDATE_MEMBER);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/me")
    public SmeemResponse<RetrieveMemberResponse> retrieveMember(Principal principal) {
        val memberId = smeemConverter.toMemberId(principal);
        return SmeemResponse.of(
                memberUseCase.retrieveMember(memberId),
                SmeemMessage.RETRIEVE_MEMBER);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/nickname/check")
    public SmeemResponse<UsernameDuplicatedResponse> checkUsernameDuplicated(@RequestParam String name) {
        return SmeemResponse.of(
                memberUseCase.checkUsernameDuplicated(name),
                SmeemMessage.RETRIEVE_MEMBER);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/push")
    public SmeemResponse<?> updateMemberHasPushAlarm(
            Principal principal,
            @RequestBody UpdateMemberHasPushAlarmRequest request
    ) {
        val memberId = smeemConverter.toMemberId(principal);
        memberUseCase.updateMemberHasPush(memberId, request);
        return SmeemResponse.of(SmeemMessage.UPDATE_MEMBER);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/performance/summary")
    public SmeemResponse<RetrievePerformanceResponse> retrievePerformance(Principal principal) {
        val memberId = smeemConverter.toMemberId(principal);
        return SmeemResponse.of(
                memberUseCase.retrieveMemberPerformance(memberId),
                SmeemMessage.RETRIEVE_MEMBER);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/visit")
    public SmeemResponse<?> checkAttendance(Principal principal) {
        val memberId = smeemConverter.toMemberId(principal);
        memberUseCase.checkAttendance(memberId);
        return SmeemResponse.of(SmeemMessage.UPDATE_MEMBER);
    }
}
