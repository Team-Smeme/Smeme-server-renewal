package com.smeem.application.domain.diary;

import com.smeem.application.domain.generic.LangType;
import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
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

    public void validateDiaryOwnership(long memberId) {
        if (this.memberId != memberId) {
            throw new SmeemException(ExceptionCode.INVALID_MEMBER_AND_DIARY);
        }
    }

    public boolean isUpdated() {
        return updatedAt.isAfter(createdAt);
    }
}
