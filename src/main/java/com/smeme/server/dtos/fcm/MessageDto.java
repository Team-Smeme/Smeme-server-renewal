package com.smeme.server.dtos.fcm;

import lombok.Builder;

@Builder
public record MessageDto(
	boolean validateOnly,
	Message message
) {
	public static MessageDto of(String targetToken, String title, String body) {
		Notification notification = new Notification(title, body);
		Message message = new Message(notification, targetToken);
		return new MessageDto(false, message);
	}
}

record Message(Notification notification, String token) { }

record Notification(String title, String body) { }
