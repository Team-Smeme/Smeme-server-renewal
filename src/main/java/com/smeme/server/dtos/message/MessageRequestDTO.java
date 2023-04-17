package com.smeme.server.dtos.message;

public record MessageRequestDTO(String targetToken, String title, String body) {
}
