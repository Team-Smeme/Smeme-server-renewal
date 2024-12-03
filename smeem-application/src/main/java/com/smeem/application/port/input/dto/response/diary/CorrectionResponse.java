package com.smeem.application.port.input.dto.response.diary;

import com.smeem.application.domain.diary.Correction;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record CorrectionResponse(
        @Schema(description = "교정 전 문장", example = "hallo")
        String originalSentence,
        @Schema(description = "교정 후 문장", example = "hello")
        String correctedSentence,
        @Schema(description = "교정 사유", example = "스펠링 틀림")
        String reason,
        @Schema(description = "교정 여부", example = "true")
        boolean isCorrected
) {

        public static CorrectionResponse from(Correction correction) {
            return CorrectionResponse.builder()
                    .originalSentence(correction.originalSentence())
                    .correctedSentence(correction.correctedSentence())
                    .reason(correction.reason())
                    .isCorrected(correction.isCorrected())
                    .build();
        }
}
