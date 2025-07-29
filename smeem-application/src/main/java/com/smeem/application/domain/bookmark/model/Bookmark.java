package com.smeem.application.domain.bookmark.model;

import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class Bookmark {
    private Long id;
    private long memberId;
    private LocalDateTime createdAt;

    /**
     * expression
     */
    private String expression;
    private String translatedExpression;

    /**
     * scrap
     */
    private String thumbnailImageUrl;
    private String scrapedUrl;
    private String description;

    public void validateBookmarkOwnership(long memberId) {
        if (this.memberId != memberId) {
            throw new SmeemException(ExceptionCode.INVALID_MEMBER_AND_BOOKMARK);
        }
    }
}

