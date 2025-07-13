package com.smeem.application.domain.bookmark;

import lombok.Builder;

@Builder
public record Expression(
        String expression,
        String translatedExpression
) {
}
