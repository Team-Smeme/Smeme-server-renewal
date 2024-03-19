package com.smeem.api.member.service.dto.request;


import com.smeem.api.member.api.dto.request.MemberUpdateRequest;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record MemberServiceUpdateUserProfileRequest(
        String username,
        Boolean termAccepted
) {

    public static MemberServiceUpdateUserProfileRequest of(MemberUpdateRequest request) {
        return MemberServiceUpdateUserProfileRequest.builder()
                .username(request.username())
                .termAccepted(request.termAccepted())
                .build();
    }
}
