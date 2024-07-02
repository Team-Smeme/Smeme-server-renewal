package com.smeem.application.domain.diary;

import com.smeem.application.domain.generic.LangType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Diary {
    Long id;
    String content;
    LangType targetLang;
    Long topicId;
    long memberId;
}
