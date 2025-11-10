package com.smeem.application.port.output.persistence;

import com.smeem.application.domain.bookmark.model.Bookmark;

import java.util.List;
import java.util.Map;

public interface BookmarkPort {
    Bookmark save(Bookmark bookmark);

    Map<String /* scrapedUrl */, Bookmark> getPerScrapedUrlOverCount(int count);

    Bookmark getByScrapedUrl(String scrapedUrl);

    void deleteByMemberId(long memberId);

    void deleteByBookmarkId(long bookmarkId);

    Bookmark getById(long bookmarkId);

    List<Bookmark> getByMemberId(long memberId);

    Bookmark update(Bookmark bookmark);
}
