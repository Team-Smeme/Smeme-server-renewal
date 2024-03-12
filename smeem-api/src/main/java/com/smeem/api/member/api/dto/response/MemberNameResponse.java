package com.smeem.api.member.api.dto.response;

import com.smeem.api.member.service.dto.response.MemberNameServiceResponse;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record MemberNameResponse(
        boolean isExist
) {

    public static MemberNameResponse from(MemberNameServiceResponse response) {
        return MemberNameResponse.builder()
                .isExist(response.isExist())
                .build();
    }

}
