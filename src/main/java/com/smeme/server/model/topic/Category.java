package com.smeme.server.model.topic;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
	TEST("테스트");

	private final String name;
}
