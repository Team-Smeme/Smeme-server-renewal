package com.smeme.server.util.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseMessage {
	// message
	SUCCESS_PUSH_MESSAGE("푸시알람 요청 성공"),

	// oauth
	SUCCESS_SIGNIN("소셜로그인 성공"),

	// jwt
	SUCCESS_ISSUE_TOKEN("토큰 발급 성공");

	private final String message;
}
