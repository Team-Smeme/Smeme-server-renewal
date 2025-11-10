package com.smeem.application.domain.bookmark;

import com.smeem.application.domain.bookmark.model.Bookmark;
import com.smeem.application.port.input.bookmark.BookmarkQueryUseCase;
import com.smeem.application.port.input.bookmark.dto.BookmarkDetailResponse;
import com.smeem.application.port.input.bookmark.dto.BookmarkListResponse;
import com.smeem.application.port.output.persistence.BookmarkPort;
import com.smeem.application.port.output.web.scrap.ScrapInfo;
import com.smeem.application.port.output.web.scrap.ScrapPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkQueryService implements BookmarkQueryUseCase {

    private final BookmarkPort bookmarkPort;
    private final ScrapPort scrapPort;

    @Transactional //todo. will be removed next sprint
    @Override
    public BookmarkDetailResponse getBookmarkDetail(long memberId, long bookmarkId) {
        return Optional.ofNullable(bookmarkPort.getById(bookmarkId))
                .filter(bookmark -> bookmark.getMemberId() == memberId)
                .map(this::refresh) //todo. will be removed next sprint
                .map(BookmarkDetailResponse::from)
                .orElse(null);
    }

    //todo. will be removed next sprint
    private Bookmark refresh(@NonNull Bookmark bookmark) {
        boolean isValid = Optional.ofNullable(bookmark.getUpdateAt())
                .map(LocalDateTime::toLocalDate)
                .map(date -> ChronoUnit.DAYS.between(date, LocalDate.now()) <= 6)
                .orElse(true);

        if (isValid) {
            return bookmark;
        }

        ScrapInfo scrapInfo = scrapPort.scrap(bookmark.getScrapedUrl());
        bookmark.setThumbnailImageUrl(scrapInfo.image());
        bookmarkPort.update(bookmark);
        return bookmark;
    }

    @Override
    public BookmarkListResponse getBookmarks(long memberId) {
        List<Bookmark> bookmarks = bookmarkPort.getByMemberId(memberId).stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .toList();
        return BookmarkListResponse.from(bookmarks);
    }
}
