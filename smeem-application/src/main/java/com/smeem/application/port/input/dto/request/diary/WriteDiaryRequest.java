package com.smeem.application.port.input.dto.request.diary;

import com.smeem.application.domain.diary.Diary;
import com.smeem.application.domain.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NonNull;

public record WriteDiaryRequest(
        @Schema(description = "작성한 내용")
        @NonNull
        String content,
        @Schema(description = "일기 주제 id")
        Long topicId
) {

        public Diary toDomain(Member member) {
                return Diary.builder()
                        .content(content)
                        .targetLang(member.getTargetLang())
                        .topicId(topicId)
                        .memberId(member.getId())
                        .build();
        }

        public Diary toDomain(Diary originDiary) {
                return Diary.builder()
                        .content(content)
                        .targetLang(originDiary.getTargetLang())
                        .topicId(topicId)
                        .memberId(originDiary.getMemberId())
                        .build();
        }
}
