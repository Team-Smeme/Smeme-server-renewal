package com.smeme.server.model.goal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GoalType {
	DEVELOP("자기계발"),
	HOBBY("취미로 즐기기"),
	APPLY("현지 언어에 적응하기"),
	EXAM("외국어 시험 고득점하기"),
	BOOK("외국어 원서 독해"),
	NONE("아직 모르겠어요");

	private final String description;
}
