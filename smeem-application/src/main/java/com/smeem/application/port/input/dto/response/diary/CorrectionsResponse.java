package com.smeem.application.port.input.dto.response.diary;

import com.smeem.application.domain.diary.Correction;
import com.smeem.application.domain.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record CorrectionsResponse(
        @Schema(description = "코칭 결과")
        List<CorrectionResponse> corrections,
        @Schema(description = "회원 이름")
        String username,
        @Schema(description = "첨삭 횟수")
        int totalCount
) {

    public static CorrectionsResponse of(
            List<Correction> corrections,
            Member member,
            int totalCount
    ) {
        return CorrectionsResponse.builder()
                .corrections(corrections.stream().map(CorrectionResponse::from).toList())
                .username(member.getUsername())
                .totalCount(totalCount)
                .build();
    }
}
