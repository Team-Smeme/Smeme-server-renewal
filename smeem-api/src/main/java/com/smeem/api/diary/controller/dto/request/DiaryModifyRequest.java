package com.smeem.api.diary.controller.dto.request;

import lombok.NonNull;

public record DiaryModifyRequest(
        @NonNull
        String content,
        Long topicId
) {
}
