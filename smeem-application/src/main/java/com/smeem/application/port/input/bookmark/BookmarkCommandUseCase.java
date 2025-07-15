package com.smeem.application.port.input.bookmark;

import com.smeem.application.port.input.bookmark.dto.BookmarkFallbackRequest;
import com.smeem.application.port.input.bookmark.dto.BookmarkRequest;
import com.smeem.application.port.input.bookmark.dto.BookmarkResponse;

public interface BookmarkCommandUseCase {
    BookmarkResponse bookmark(long memberId, BookmarkRequest request);
    BookmarkResponse bookmarkFallback(long memberId, BookmarkFallbackRequest request);
}
