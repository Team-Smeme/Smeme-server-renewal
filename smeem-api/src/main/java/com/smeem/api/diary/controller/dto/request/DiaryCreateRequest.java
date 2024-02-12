package com.smeem.api.diary.controller.dto.request;

import lombok.NonNull;

public record DiaryCreateRequest(
        @NonNull
        String content,
        Long topicId
) {
}
