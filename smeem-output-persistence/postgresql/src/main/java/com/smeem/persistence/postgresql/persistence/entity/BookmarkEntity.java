package com.smeem.persistence.postgresql.persistence.entity;

import com.smeem.application.domain.bookmark.model.Bookmark;
import com.smeem.application.domain.bookmark.model.ScrapType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.smeem.application.util.DomainConstant.BOOKMARK;
import static com.smeem.application.util.DomainConstant.SMEEM;

@Entity
@Table(name = BOOKMARK, schema = SMEEM)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private long memberId;

    @Column(nullable = false)
    private String expression;

    @Column(nullable = false)
    private String translatedExpression;

    @Column(length = 1500)
    private String thumbnailImageUrl;

    @Column
    private String scrapedUrl;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(value = EnumType.STRING)
    private ScrapType scrapType;

    public BookmarkEntity(Bookmark domain) {
        this.memberId = domain.getMemberId();
        this.expression = domain.getExpression();
        this.translatedExpression = domain.getTranslatedExpression();
        this.thumbnailImageUrl = domain.getThumbnailImageUrl();
        this.scrapedUrl = domain.getScrapedUrl();
        this.description = domain.getDescription();
        this.scrapType = domain.getScrapType();
    }

    public Bookmark toDomain() {
        return Bookmark.builder()
                .id(this.id)
                .memberId(this.memberId)
                .expression(this.expression)
                .translatedExpression(this.translatedExpression)
                .thumbnailImageUrl(this.thumbnailImageUrl)
                .scrapedUrl(this.scrapedUrl)
                .description(this.description)
                .createdAt(this.createdAt)
                .scrapType(this.scrapType)
                .build();
    }

    public BookmarkEntity update(Bookmark bookmark) {
        this.expression = bookmark.getExpression();
        this.translatedExpression = bookmark.getTranslatedExpression();
        this.description = bookmark.getDescription();
        return this;
    }
}
