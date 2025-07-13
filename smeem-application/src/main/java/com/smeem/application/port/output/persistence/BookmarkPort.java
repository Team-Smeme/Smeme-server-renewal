package com.smeem.application.port.output.persistence;

import com.smeem.application.domain.bookmark.Bookmark;

import java.util.Map;

public interface BookmarkPort {
    Bookmark save(Bookmark bookmark);

    Map<String /* scrapedUrl */, Bookmark> getPerScrapedUrlOverCount(int count);

    Bookmark getByScrapedUrl(String scrapedUrl);

    void deleteByMemberId(long memberId);
}
