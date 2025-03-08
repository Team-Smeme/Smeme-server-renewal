package com.smeem.application.domain.survey;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DissatisfactionReason {
    HARD_TO_UNDERSTAND("이해하기 어려움"),
    TOO_SHORT("너무 짧음"),
    FEEDBACK_ERROR("피드백 오류"),
    WORD_FEEDBACK_LACK("단어 피드백 부족"),
    GRAMMAR_FEEDBACK_LACK("문법 피드백 부족"),
    ;

    private final String description;
}