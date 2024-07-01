package com.smeem.persistence.postgresql.persistence.entity;

import com.smeem.application.domain.badge.Badge;
import com.smeem.application.domain.badge.BadgeType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BadgeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    private BadgeType badgeType;
    private int standard;
    private String name;
    @Embedded
    private BadgeImage badgeImage;
    private String messageNotAcquired;
    private String messageAcquired;
    private float acquisitionRate;

    public Badge toDomain() {
        return Badge.builder()
                .id(id)
                .badgeType(badgeType)
                .standard(standard)
                .name(name)
                .colorImageUrl(badgeImage.getImageUrl())
                .grayImageUrl(badgeImage.getGrayImageUrl())
                .messageAcquired(messageAcquired)
                .messageNotAcquired(messageNotAcquired)
                .acquisitionRate(acquisitionRate)
                .build();
    }
}
