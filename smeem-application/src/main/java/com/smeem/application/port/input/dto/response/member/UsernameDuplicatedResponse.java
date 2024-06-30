package com.smeem.application.port.input.dto.response.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record UsernameDuplicatedResponse(
        @Schema(description = "중복 여부")
        boolean isExist
) {
}
