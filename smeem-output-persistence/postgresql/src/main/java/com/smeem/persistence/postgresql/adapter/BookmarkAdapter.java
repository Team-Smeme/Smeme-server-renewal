package com.smeem.persistence.postgresql.adapter;

import com.smeem.application.domain.bookmark.Bookmark;
import com.smeem.application.port.output.persistence.BookmarkPort;
import com.smeem.persistence.postgresql.persistence.entity.BookmarkEntity;
import com.smeem.persistence.postgresql.persistence.repository.bookmark.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
}
