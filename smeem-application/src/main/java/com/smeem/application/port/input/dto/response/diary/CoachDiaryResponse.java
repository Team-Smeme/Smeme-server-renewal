package com.smeem.application.port.input.dto.response.diary;

import com.smeem.application.domain.diary.Correction;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record CoachDiaryResponse(
        @Schema(description = "교정 정보")
        List<Correction> corrections
) {

    public static CoachDiaryResponse of(List<Correction> corrections) {
        return CoachDiaryResponse.builder()
                .corrections(corrections)
                .build();
    }
}
