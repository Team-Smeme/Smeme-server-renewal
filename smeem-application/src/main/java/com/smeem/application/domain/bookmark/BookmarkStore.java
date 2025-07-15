package com.smeem.application.domain.bookmark;

import com.smeem.application.domain.bookmark.model.Bookmark;
import com.smeem.application.port.output.persistence.BookmarkPort;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class BookmarkStore {

    private final BookmarkPort bookmarkPort;

    private Map<String, Bookmark> bookmarkMap = new HashMap<>();
    private static final int TARGET_COUNT = 5;

    @PostConstruct
    @Scheduled(cron = "0 0 3 * * *")
    private void init() {
        this.bookmarkMap = bookmarkPort.getPerScrapedUrlOverCount(TARGET_COUNT);
    }

    public Bookmark getByScrapedUrl(String url) {
        if (url == null) {
            return null;
        }
        return bookmarkMap.containsKey(url) ? bookmarkMap.get(url) : bookmarkPort.getByScrapedUrl(url);
    }
}
