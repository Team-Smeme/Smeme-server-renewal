package com.smeem.application.port.input.bookmark;

import com.smeem.application.port.input.bookmark.dto.BookmarkDetailResponse;
import com.smeem.application.port.input.bookmark.dto.BookmarkListResponse;
import com.smeem.application.port.input.bookmark.dto.BookmarkModifyResponse;
import com.smeem.application.port.input.bookmark.dto.BookmarkUpdateRequest;

public interface BookmarkQueryUseCase {
    BookmarkDetailResponse getBookmarkDetail(long memberId, long bookmarkId);
    BookmarkListResponse getBookmarks(long memberId);
    void deleteBookmark(long memberId, long bookmarkId);
    BookmarkModifyResponse updateBookmark(long memberId, long bookmarkId, BookmarkUpdateRequest request);
}
