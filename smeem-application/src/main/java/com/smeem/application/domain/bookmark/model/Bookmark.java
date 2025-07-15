package com.smeem.application.domain.bookmark.model;

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
}

