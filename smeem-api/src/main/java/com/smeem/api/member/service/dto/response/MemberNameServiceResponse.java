package com.smeem.api.member.service.dto.response;


import lombok.Builder;

import static lombok.AccessLevel.*;

@Builder(access = PRIVATE)
public record MemberNameServiceResponse(
        boolean isExist
) {
    public static MemberNameServiceResponse of(boolean isExist) {
        return MemberNameServiceResponse
                .builder()
                .isExist(isExist)
                .build();
    }
}
