package com.smeem.api.member.service.dto.request;


import com.smeem.api.member.api.dto.request.MemberUpdateRequest;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record MemberServiceUpdateUserProfileRequest(
        long memberId,
        String username,
        Boolean termAccepted
) {

    public static MemberServiceUpdateUserProfileRequest of(long memberId, MemberUpdateRequest request) {
        return MemberServiceUpdateUserProfileRequest.builder()
                .memberId(memberId)
                .username(request.username())
                .termAccepted(request.termAccepted())
                .build();
    }
}
