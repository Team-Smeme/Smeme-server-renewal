package com.smeem.api.diary.api.dto.request;

import lombok.NonNull;

public record DiaryModifyRequest(
        @NonNull
        String content,
        Long topicId
) {
}
