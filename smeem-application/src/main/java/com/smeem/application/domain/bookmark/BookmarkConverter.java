package com.smeem.application.domain.bookmark;

import com.smeem.application.domain.bookmark.model.Bookmark;
import com.smeem.application.domain.bookmark.model.Expression;
import com.smeem.application.port.input.bookmark.dto.BookmarkFallbackRequest;
import com.smeem.application.port.output.web.scrap.ScrapInfo;
import lombok.NonNull;

public class BookmarkConverter {

    public static Bookmark convert(@NonNull ScrapInfo scrapInfo, @NonNull Expression expression, long memberId) {
        return Bookmark.builder()
                .memberId(memberId)
                .description(scrapInfo.description())
                .scrapedUrl(scrapInfo.url())
                .thumbnailImageUrl(scrapInfo.image())
                .expression(expression.expression())
                .translatedExpression(expression.translatedExpression())
                .build();
    }

    public static Bookmark convert(@NonNull BookmarkFallbackRequest request, long memberId) {
        return Bookmark.builder()
                .memberId(memberId)
                .description(request.description())
                .scrapedUrl(request.scrapedUrl())
                .thumbnailImageUrl(request.thumbnailImageUrl())
                .expression(request.expression())
                .translatedExpression(request.translatedExpression())
                .build();
    }

    public static Bookmark convert(long memberId, Bookmark bookmark) {
        return Bookmark.builder()
                .memberId(memberId)
                .description(bookmark.getDescription())
                .scrapedUrl(bookmark.getScrapedUrl())
                .thumbnailImageUrl(bookmark.getThumbnailImageUrl())
                .expression(bookmark.getExpression())
                .translatedExpression(bookmark.getTranslatedExpression())
                .build();
    }
}
