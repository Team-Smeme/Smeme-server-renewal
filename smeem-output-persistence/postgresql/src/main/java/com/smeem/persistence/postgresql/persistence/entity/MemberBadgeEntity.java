package com.smeem.persistence.postgresql.persistence.entity;

import com.smeem.application.domain.badge.Badge;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberBadgeEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private long memberId;
    @Column(nullable = false)
    private long badgeId;

    public MemberBadgeEntity(long memberId, long badgeId) {
        this.memberId = memberId;
        this.badgeId = badgeId;
    }
}
