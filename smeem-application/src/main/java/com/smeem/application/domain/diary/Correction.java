package com.smeem.application.domain.diary;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record Correction(
        @Schema(description = "교정 전 문장", example = "hallo")
        @JsonProperty("original_sentence")
        String originalSentence,
        @Schema(description = "교정 후 문장", example = "hello")
        @JsonProperty("corrected_sentence")
        String correctedSentence,
        @Schema(description = "교정 사유", example = "스펠링 틀림")
        @JsonProperty("reason")
        String reason,
        @Schema(description = "교정 여부", example = "true")
        @JsonProperty("is_corrected")
        boolean isCorrected
) {
}
