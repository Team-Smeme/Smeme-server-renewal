package com.smeme.server.model.badge;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BadgeType {
	WELCOME("웰컴 배지"),
	ACCUMULATE("일기 누적수"),
	CONTINUITY("일기 연속 수"),
	ETC("부가 기능 활용");

	private final String description;
}
