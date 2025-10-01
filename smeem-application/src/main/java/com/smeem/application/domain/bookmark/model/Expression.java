package com.smeem.application.domain.bookmark.model;

import io.micrometer.common.util.StringUtils;
import lombok.Builder;

@Builder
public record Expression(
        String expression,
        String translatedExpression
) {

    public boolean validate() {
        return isEnglish(this.expression) && isKorean(this.translatedExpression);
    }

    //todo. Utils 용도로 빼기 (e.g. LanguageGenerator)
    private boolean isEnglish(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        return str.matches("^[a-zA-Z\\s]+$");
    }

    private boolean isKorean(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        return str.matches("^[가-힣\\s]+$");
    }
}
