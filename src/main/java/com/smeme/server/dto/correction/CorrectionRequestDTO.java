package com.smeme.server.dto.correction;

import io.swagger.v3.oas.annotations.media.Schema;

public record CorrectionRequestDTO(
        @Schema(description = "첨삭할 문장", example = "Hello")
        String sentence,

        @Schema(description = "첨삭 내용", example = "Hi")
        String content
) {
}
