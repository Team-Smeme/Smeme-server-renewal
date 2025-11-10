package com.smeem.persistence.postgresql.persistence.repository.bookmark;

import com.smeem.persistence.postgresql.persistence.entity.BookmarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {

    @Query(BookmarkQueryConstants.findPerScrapedUrlOverCount)
    List<BookmarkEntity> findPerScrapedUrlOverCount(int count);

    Optional<BookmarkEntity> findFirstByScrapedUrl(String scrapedUrl);

    void deleteByMemberId(long memberId);

    void deleteById(long id);

    List<BookmarkEntity> findByMemberId(long memberId);
}
