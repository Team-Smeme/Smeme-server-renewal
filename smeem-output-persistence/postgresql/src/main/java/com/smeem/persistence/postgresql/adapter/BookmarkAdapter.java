package com.smeem.persistence.postgresql.adapter;

import com.smeem.application.domain.bookmark.model.Bookmark;
import com.smeem.application.port.output.persistence.BookmarkPort;
import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import com.smeem.persistence.postgresql.persistence.entity.BookmarkEntity;
import com.smeem.persistence.postgresql.persistence.repository.bookmark.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookmarkAdapter implements BookmarkPort {

    private final BookmarkRepository bookmarkRepository;

    public Bookmark save(Bookmark bookmark) {
        BookmarkEntity savedEntity = bookmarkRepository.save(new BookmarkEntity(bookmark));
        return savedEntity.toDomain();
    }

    public Map<String /* scrapedUrl */, Bookmark> getPerScrapedUrlOverCount(int count) {
        return bookmarkRepository.findPerScrapedUrlOverCount(count)
                .stream()
                .map(BookmarkEntity::toDomain)
                .collect(Collectors.toMap(
                        Bookmark::getScrapedUrl,
                        Function.identity()
                ));
    }

    public Bookmark getByScrapedUrl(String url) {
        return bookmarkRepository.findFirstByScrapedUrl(url)
                .map(BookmarkEntity::toDomain)
                .orElse(null);
    }

    @Override
    public void deleteByMemberId(long memberId) {
        bookmarkRepository.deleteByMemberId(memberId);
    }

    @Override
    public void deleteByBookmarkId(long bookmarkId) {
        find(bookmarkId);
        bookmarkRepository.deleteById(bookmarkId);
    }

    @Override
    public Bookmark getById(long bookmarkId) {
        return find(bookmarkId).toDomain();
    }

    @Override
    public List<Bookmark> getByMemberId(long memberId) {
        return bookmarkRepository.findByMemberId(memberId)
                .stream()
                .map(BookmarkEntity::toDomain)
                .toList();
    }

    @Override
    public Bookmark update(Bookmark bookmark) {
        BookmarkEntity foundBookmark = find(bookmark.getId());
        return foundBookmark.update(bookmark).toDomain();
    }

    private BookmarkEntity find(long id) {
        return bookmarkRepository.findById(id)
                .orElseThrow(() -> new SmeemException(ExceptionCode.NOT_FOUND, "(Bookmark ID: " + id + ")"));
    }
}
