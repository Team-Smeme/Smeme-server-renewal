package com.smeem.application.domain.bookmark.model;

import lombok.Builder;

@Builder
public record Expression(
        String expression,
        String translatedExpression
) {
}
