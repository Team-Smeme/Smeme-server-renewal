package com.smeem.persistence.postgresql.persistence.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BadgeImage {
    private String grayImageUrl;
    private String imageUrl;
}
