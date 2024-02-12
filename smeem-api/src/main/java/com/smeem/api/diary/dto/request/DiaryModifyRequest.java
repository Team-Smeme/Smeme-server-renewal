package com.smeem.api.diary.dto.request;

import lombok.NonNull;

public record DiaryModifyRequest(
        @NonNull
        String content,
        Long topicId
) {
}
