package com.smeem.persistence.postgresql.persistence.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_badge", schema = "smeem")
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
