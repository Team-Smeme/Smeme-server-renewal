package com.smeme.server.controller;

import com.smeme.server.dto.member.UpdateMemberRequestDTO;
import com.smeme.server.service.MemberService;
import com.smeme.server.util.ApiResponse;
import com.smeme.server.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.smeme.server.util.message.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/members")
public class MemberController {

    private final MemberService memberService;

    @PatchMapping()
    public ResponseEntity<ApiResponse> updateUserProfile(
            Principal principal, @RequestBody UpdateMemberRequestDTO requestDTO) {
        memberService.updateMember(Util.getMemberId(principal), requestDTO);
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_UPDATE_USERNAME.getMessage()));
    }

}
