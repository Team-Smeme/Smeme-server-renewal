package com.smeem.api.member.controller.dto.response;

import com.smeem.api.member.service.dto.response.MemberNameServiceResponse;

public record MemberNameResponse(
        boolean isExist
) {

    public static MemberNameResponse of(MemberNameServiceResponse response) {
        return new MemberNameResponse(response.isExist());
    }

}
