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
        List<CorrectionResponse> corrections
) {

    public static CorrectionsResponse of(
            List<Correction> corrections
    ) {
        return CorrectionsResponse.builder()
                .corrections(corrections.stream().map(CorrectionResponse::from).toList())
                .build();
    }
}
