package com.smeem.application.domain.badge;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BadgeType {
    EVENT("Event"),
    COUNTING("Diary Counting"),
    COMBO("Diary Combo"),
    EXPLORATION("Exploration");

    private final String description;
}
