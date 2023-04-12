package com.smeme.server.dtos.fcm;

public record MessageRequestDto(String targetToken, String title, String body) {
}
