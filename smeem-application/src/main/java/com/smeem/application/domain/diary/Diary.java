package com.smeem.application.domain.diary;

import com.smeem.application.domain.generic.LangType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Diary {
    private Long id;
    private String content;
    private LangType targetLang;
    private Long topicId;
    private long memberId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String engKorExpression;

    public boolean isUpdated() {
        return updatedAt.isAfter(createdAt);
    }
}
