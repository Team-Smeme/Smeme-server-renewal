package com.smeem.application.port.input;

import com.smeem.application.port.input.dto.request.bookmark.BookmarkFallbackRequest;
import com.smeem.application.port.input.dto.request.bookmark.BookmarkRequest;
import com.smeem.application.port.input.dto.response.bookmark.BookmarkResponse;

public interface BookmarkUseCase {
    BookmarkResponse bookmark(long memberId, BookmarkRequest request);
    BookmarkResponse bookmarkFallback(long memberId, BookmarkFallbackRequest request);
}
