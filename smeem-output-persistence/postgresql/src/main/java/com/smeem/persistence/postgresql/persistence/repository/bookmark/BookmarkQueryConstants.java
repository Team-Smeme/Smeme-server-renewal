package com.smeem.persistence.postgresql.persistence.repository.bookmark;

public class BookmarkQueryConstants {

    public static final String findPerScrapedUrlOverCount = """
            SELECT b FROM BookmarkEntity b
            WHERE b.id IN (
                SELECT MAX(b2.id)
                FROM BookmarkEntity b2
                GROUP BY b2.scrapedUrl
                HAVING COUNT(b2) >= :count
            )
           """;
}
