package com.smeem.http.controller;

import com.smeem.application.port.input.MemberUseCase;
import com.smeem.application.port.input.dto.request.member.UpdateMemberHasPushAlarmRequest;
import com.smeem.application.port.input.dto.request.member.UpdateMemberRequest;
import com.smeem.application.port.input.dto.response.SmeemResponse;
import com.smeem.application.port.input.dto.response.member.RetrieveMemberResponse;
import com.smeem.application.port.input.dto.response.member.RetrievePerformanceResponse;
import com.smeem.application.port.input.dto.response.member.UpdateMemberResponse;
import com.smeem.application.port.input.dto.response.member.UsernameDuplicatedResponse;
import com.smeem.http.controller.docs.MemberApiDocs;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
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

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    public SmeemResponse<UpdateMemberResponse> updateMember(
            Principal principal,
            @RequestBody UpdateMemberRequest request
    ) {
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/me")
    public SmeemResponse<RetrieveMemberResponse> retrieveMember(Principal principal) {
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/nickname/check")
    public SmeemResponse<UsernameDuplicatedResponse> checkUsernameDuplicated(@RequestParam String name) {
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/push")
    public SmeemResponse<?> updateMemberHasPushAlarm(
            Principal principal,
            @RequestBody UpdateMemberHasPushAlarmRequest request
    ) {
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/performance/summary")
    public SmeemResponse<RetrievePerformanceResponse> retrieveSummary(Principal principal) {
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/visit")
    public SmeemResponse<?> checkAttendance(Principal principal) {
        return null;
    }
}
