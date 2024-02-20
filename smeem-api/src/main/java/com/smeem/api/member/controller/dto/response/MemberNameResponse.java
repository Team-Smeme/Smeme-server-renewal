package com.smeem.api.member.controller.dto.response;

import com.smeem.api.member.service.dto.response.MemberNameServiceResponse;
import lombok.AccessLevel;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record MemberNameResponse(
        boolean isExist
) {

    public static MemberNameResponse of(MemberNameServiceResponse response) {
        return MemberNameResponse.builder()
                .isExist(response.isExist())
                .build();
    }

}
