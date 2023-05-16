package com.smeme.server.util.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

	// auth
	EMPTY_ACCESS_TOKEN("액세스 토큰이 없습니다."),
	EMPTY_REFRESH_TOKEN("리프레시 토큰이 없습니다"),
	INVALID_TOKEN("유효하지 않은 토큰입니다"),

	// member
	EMPTY_MEMBER("존재하지 않는 회원입니다."),
	DUPLICATE_USERNAME("이미 존재하는 닉네임입니다."),
	INVALID_MEMBER("유효하지 않은 회원입니다."),
	INVALID_USERNAME("유효하지 않은 닉네임입니다."),

	// diary
	EXIST_TODAY_DIARY("일기는 하루에 한 번씩 작성할 수 있습니다."),
	INVALID_DIARY("유효하지 않은 일기입니다."),
	DELETED_DIARY("삭제된 일기입니다."),

	// correction
	INVALID_CORRECTION("유효하지 않은 첨삭입니다."),

	// topic
	INVALID_TOPIC("유효하지 않은 랜덤주제입니다."),

	// Push Alarm
	INVALID_DAY_OF_WEEK("유효하지 않은 요일 값입니다.")
	;

	private final String message;
}
