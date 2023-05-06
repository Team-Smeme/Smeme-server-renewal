package com.smeme.server.util.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseMessage {
	// diary
	SUCCESS_CREATE_DIARY("일기 생성 성공"),
	SUCCESS_GET_DIARY("일기 상세 조회 성공"),

	// message
	SUCCESS_PUSH_MESSAGE("푸시알람 요청 성공");

	private final String message;
}
