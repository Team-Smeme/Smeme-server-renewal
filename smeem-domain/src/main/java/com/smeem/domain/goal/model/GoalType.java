package com.smeem.domain.goal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PROTECTED;

@AllArgsConstructor(access = PROTECTED)
@Getter
public enum GoalType {
    DEVELOP("자기계발"),
    HOBBY("취미로 즐기기"),
    APPLY("현지 언어 체득"),
    BUSINESS("유창한 비즈니스 영어"),
    EXAM("어학 시험 고득점"),
    NONE("아직 모르겠어요");

    private final String description;
}
