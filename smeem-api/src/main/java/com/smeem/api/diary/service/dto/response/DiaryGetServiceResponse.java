package com.smeem.api.diary.service.dto.response;

import static java.util.Objects.*;
import static lombok.AccessLevel.PRIVATE;

import com.smeem.domain.diary.model.Diary;
import com.smeem.domain.topic.model.Topic;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(access = PRIVATE)
public record DiaryGetServiceResponse(
        long diaryId,
        String topicContent,
        String content,
        LocalDateTime createdAt,
        String username
) {

    public static DiaryGetServiceResponse of(Diary diary) {
        return DiaryGetServiceResponse.builder()
                .diaryId(diary.getId())
                .topicContent(getTopicContent(diary.getTopic()))
                .content(diary.getContent())
                .createdAt(diary.getCreatedAt())
                .username(diary.getMember().getUsername())
                .build();
    }

    private static String getTopicContent(Topic topic) {
        return nonNull(topic) ? topic.getContent() : "";
    }
}

