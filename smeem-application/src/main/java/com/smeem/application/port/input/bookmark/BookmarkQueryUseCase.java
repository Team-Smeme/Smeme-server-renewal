package com.smeem.application.port.input.bookmark;

import com.smeem.application.port.input.bookmark.dto.BookmarkDetailResponse;
import com.smeem.application.port.input.bookmark.dto.BookmarkListResponse;

public interface BookmarkQueryUseCase {
    BookmarkDetailResponse getBookmarkDetail(long memberId, long bookmarkId);
    BookmarkListResponse getBookmarks(long memberId);
}
