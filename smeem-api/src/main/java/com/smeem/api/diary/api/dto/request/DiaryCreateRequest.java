package com.smeem.api.diary.api.dto.request;

import lombok.NonNull;

public record DiaryCreateRequest(
        @NonNull
        String content,
        Long topicId
) {
}
