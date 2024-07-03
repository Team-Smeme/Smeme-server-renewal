package com.smeem.application.port.input.dto.request.diary;

import com.smeem.application.domain.diary.Diary;
import com.smeem.application.domain.member.Member;
import com.smeem.application.domain.topic.Topic;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NonNull;

public record WriteDiaryRequest(
        @Schema(description = "작성한 내용")
        @NonNull
        String content,
        @Schema(description = "일기 주제 id")
        Long topicId
) {

        public Diary toDomain(Member member, Topic topic) {
                return Diary.builder()
                        .content(content)
                        .targetLang(member.getTargetLang())
                        .topic(topic)
                        .member(member)
                        .build();
        }

        public Diary toDomain(Diary diary) {
                return Diary.builder()
                        .id(diary.getId())
                        .content(content)
                        .targetLang(diary.getTargetLang())
                        .topic(Topic.builder().id(topicId).build())
                        .build();
        }
}
