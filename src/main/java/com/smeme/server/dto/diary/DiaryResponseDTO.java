package com.smeme.server.dto.diary;

import static com.smeme.server.util.Util.dateToString;
import static java.util.Objects.*;

import java.util.List;

import com.smeme.server.model.Correction;
import com.smeme.server.model.Diary;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

@Schema(description = "일기 조회 응답 DTO")
@Builder(access = AccessLevel.PRIVATE)
public record DiaryResponseDTO(
        @Schema(description = "일기 id", example = "1")
        Long diaryId,

        @Schema(description = "랜덤 주제", example = "가보고 싶은 해외 여행지가 있다면 소개해 주세요!")
        String topic,

        @Schema(description = "일기 내용", example = "Hello Smeem")
        String content,

        @Schema(description = "생성일자", example = "2023-08-01 14:00")
        String createdAt,

        @Schema(description = "작성자 이름", example = "스미미")
        String username,

        @Schema(description = "첨삭 리스트")
        List<CorrectionDTO> corrections
) {
    public static DiaryResponseDTO of(Diary diary) {
        return DiaryResponseDTO.builder()
                .diaryId(diary.getId())
                .topic(nonNull(diary.getTopic()) ? diary.getTopic().getContent() : "")
                .content(diary.getContent())
                .createdAt(dateToString(diary.getCreatedAt()))
                .username(diary.getMember().getUsername())
                .corrections(diary.getCorrections().stream().map(CorrectionDTO::of).toList())
                .build();
    }

    record CorrectionDTO(
            @Schema(description = "첨삭 id", example = "1")
            Long correctionId,

            @Schema(description = "첨삭 전 내용", example = "Hello")
            String before,

            @Schema(description = "첨삭 후 내용", example = "Hi")
            String after
    ) {
        static CorrectionDTO of(Correction correction) {
            return new CorrectionDTO(correction.getId(), correction.getBeforeSentence(), correction.getAfterSentence());
        }
    }

}

