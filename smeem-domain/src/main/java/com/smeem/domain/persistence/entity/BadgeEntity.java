package com.smeem.domain.persistence.entity;

import com.smeem.api.domain.badge.vo.BadgeImage;
import com.smeem.api.domain.badge.vo.BadgeType;
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
    private BadgeType type;

    private String name;

    @Embedded
    private BadgeImage badgeImage;

    private String messageForNonBadgeOwner;
    private String messageForBadgeOwner;
    private float badgeAcquisitionRatio;

    public void updateBadgeAcquisitionRatio(float badgeAcquisitionRatio) {
        this.badgeAcquisitionRatio = badgeAcquisitionRatio;
    }

}
