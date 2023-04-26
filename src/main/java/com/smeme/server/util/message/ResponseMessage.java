package com.smeme.server.util.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseMessage {
	// message
	SUCCESS_PUSH_MESSAGE("푸시알람 요청 성공");

	private final String message;
}
