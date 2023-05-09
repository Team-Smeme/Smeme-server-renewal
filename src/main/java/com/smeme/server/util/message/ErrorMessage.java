package com.smeme.server.util.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
	// auth
	EMPTY_ACCESS_TOKEN("액세스 토큰이 없습니다."),

	// member
	INVALID_MEMBER("유효하지 않은 회원입니다."),

	// diary
	EXIST_TODAY_DIARY("일기는 하루에 한 번씩 작성할 수 있습니다."),
	INVALID_DIARY("유효하지 않은 일기입니다."),
	DELETED_DIARY("삭제된 일기입니다."),

	// topic
	INVALID_TOPIC("유효하지 않은 랜덤주제입니다.")
	;

	private final String message;
}
