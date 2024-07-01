package com.smeem.application.domain.badge;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Badge {
    private Long id;
    private BadgeType badgeType;
    private int standard;
    private String name;
    private String colorImageUrl;
    private String grayImageUrl;
    private String messageAcquired;
    private String messageNotAcquired;
    private float acquisitionRate;
}
