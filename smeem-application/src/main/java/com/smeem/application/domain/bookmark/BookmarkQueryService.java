package com.smeem.application.domain.bookmark;

import com.smeem.application.domain.bookmark.model.Bookmark;
import com.smeem.application.port.input.bookmark.BookmarkQueryUseCase;
import com.smeem.application.port.input.bookmark.dto.BookmarkDetailResponse;
import com.smeem.application.port.input.bookmark.dto.BookmarkListResponse;
import com.smeem.application.port.output.persistence.BookmarkPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkQueryService implements BookmarkQueryUseCase {

    private final BookmarkPort bookmarkPort;

    @Override
    public BookmarkDetailResponse getBookmarkDetail(long memberId, long bookmarkId) {
        return Optional.ofNullable(bookmarkPort.getById(bookmarkId))
                .filter(bookmark -> bookmark.getMemberId() == memberId)
                .map(BookmarkDetailResponse::from)
                .orElse(null);
    }

    @Override
    public BookmarkListResponse getBookmarks(long memberId) {
        List<Bookmark> bookmarks = bookmarkPort.getByMemberId(memberId).stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .toList();
        return BookmarkListResponse.from(bookmarks);
    }
}
