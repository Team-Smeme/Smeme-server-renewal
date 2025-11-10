package com.smeem.application.port.input.bookmark;

import com.smeem.application.port.input.bookmark.dto.BookmarkFallbackRequest;
import com.smeem.application.port.input.bookmark.dto.BookmarkModifyResponse;
import com.smeem.application.port.input.bookmark.dto.BookmarkRequest;
import com.smeem.application.port.input.bookmark.dto.BookmarkResponse;
import com.smeem.application.port.input.bookmark.dto.BookmarkUpdateRequest;

public interface BookmarkCommandUseCase {
    BookmarkResponse bookmark(long memberId, BookmarkRequest request);
    BookmarkResponse bookmarkFallback(long memberId, BookmarkFallbackRequest request);
    void deleteBookmark(long memberId, long bookmarkId);
    BookmarkModifyResponse updateBookmark(long memberId, long bookmarkId, BookmarkUpdateRequest request);
}
