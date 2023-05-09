package com.smeme.server.util.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseMessage {
	// diary
	SUCCESS_CREATE_DIARY("일기 생성 성공"),
	SUCCESS_GET_DIARY("일기 상세 조회 성공"),
	SUCCESS_UPDATE_DAIRY("일기 수정 성공"),
	SUCCESS_DELETE_DIARY("일기 삭제 성공"),
	SUCCESS_GET_DIARIES("일기 리스트 조회 성공"),

	// message
	SUCCESS_PUSH_MESSAGE("푸시알람 요청 성공");

	private final String message;
}
