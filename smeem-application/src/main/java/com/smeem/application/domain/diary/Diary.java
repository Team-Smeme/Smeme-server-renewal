package com.smeem.application.domain.diary;

import com.smeem.application.domain.generic.LangType;
import com.smeem.application.domain.member.Member;
import com.smeem.application.domain.topic.Topic;
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
    private Topic topic;
    private Member member;
    private long memberId;
    private LocalDateTime createdAt;

    public void validateDiaryOwnership(long memberId) {
        if (this.memberId != memberId) {
            throw new SmeemException(ExceptionCode.INVALID_MEMBER_AND_DIARY);
        }
    }
}
