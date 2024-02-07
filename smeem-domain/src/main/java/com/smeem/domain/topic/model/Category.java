package com.smeem.domain.topic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    TRIP("여행"),
    TASTE("취향"),
    DEVELOP("자기계발"),
    PREVIEW("시사"),
    DAILY("일상"),
    ;

    private final String name;
}