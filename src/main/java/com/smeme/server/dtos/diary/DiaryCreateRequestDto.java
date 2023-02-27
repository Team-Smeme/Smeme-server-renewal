package com.smeme.server.dtos.diary;

import com.smeme.server.models.Diary;
import com.smeme.server.models.TargetLang;
import com.smeme.server.models.Topic;
import com.smeme.server.models.User;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record DiaryCreateRequestDto(
        @NonNull String content,
        @NonNull TargetLang targetLang,
        long topicId,
        boolean isPublic
) {

    public Diary toEntity(User user, Topic topic) {
        return Diary.builder()
                .user(user)
                .topic(topic)
                .content(this.content)
                .targetLang(this.targetLang)
                .isPublic(this.isPublic)
                .build();
    }
}
