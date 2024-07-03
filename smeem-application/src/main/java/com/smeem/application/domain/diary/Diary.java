package com.smeem.application.domain.diary;

import com.smeem.application.domain.generic.LangType;
import com.smeem.application.domain.member.Member;
import com.smeem.application.domain.topic.Topic;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Diary {
    Long id;
    String content;
    LangType targetLang;
    Topic topic;
    Member member;
    LocalDateTime createdAt;
}
