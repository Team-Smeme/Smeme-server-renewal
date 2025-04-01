package com.smeem.application.domain.survey;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public enum DissatisfactionType {
    HARD_TO_UNDERSTAND("이해하기 어려움"),
    TOO_SHORT("너무 짧음"),
    FEEDBACK_ERROR("피드백 오류"),
    WORD_FEEDBACK_LACK("단어 피드백 부족"),
    GRAMMAR_FEEDBACK_LACK("문법 피드백 부족"),
    ;

    private final String description;

    @JsonCreator
    public static DissatisfactionType fromString(String value) {
        try {
            return DissatisfactionType.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new SmeemException(ExceptionCode.INVALID_ENUM_TYPE, value);
        }
    }

    public static List<DissatisfactionType> fromStringArray(List<String> values) {
        return values.stream()
                .map(DissatisfactionType::fromString)
                .toList();
    }
}