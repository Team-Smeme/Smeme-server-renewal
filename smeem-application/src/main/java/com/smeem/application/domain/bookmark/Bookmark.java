package com.smeem.application.domain.bookmark;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Bookmark {
    private Long id;
    private long memberId;

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

