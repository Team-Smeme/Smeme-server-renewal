package com.smeem.api.diary.controller.dto.request;

import lombok.NonNull;

public record DiaryRequestDTO(
        @NonNull
        String content,
        Long topicId
) {
}