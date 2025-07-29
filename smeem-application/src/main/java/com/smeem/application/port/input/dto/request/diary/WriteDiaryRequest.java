package com.smeem.application.port.input.dto.request.diary;

import com.smeem.application.domain.diary.Diary;
import com.smeem.application.domain.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NonNull;

public record WriteDiaryRequest(
        @Schema(description = "작성한 내용", example = "I am very happy today")
        @NonNull
        String content,
        @Schema(description = "일기 주제 id")
        Long topicId,
        @Schema(description = "사용한 북마크 표현", example = "be happy 행복하다")
        String engKorExpression
) {

        public Diary toDomain(Member member) {
                return Diary.builder()
                        .content(content)
                        .targetLang(member.getTargetLang())
                        .topicId(topicId)
                        .memberId(member.getId())
                        .engKorExpression(engKorExpression)
                        .build();
        }

        public Diary update(Diary originDiary) {
                return Diary.builder()
                        .id(originDiary.getId())
                        .content(content)
                        .targetLang(originDiary.getTargetLang())
                        .topicId(originDiary.getTopicId())
                        .memberId(originDiary.getMemberId())
                        .build();
        }
}
