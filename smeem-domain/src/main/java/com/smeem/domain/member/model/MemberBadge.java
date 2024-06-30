package com.smeem.domain.member.model;

import static java.util.Objects.*;

import com.smeem.domain.persistence.entity.BadgeEntity;

import com.smeem.domain.common.BaseTimeEntity;
import com.smeem.domain.persistence.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberBadge extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_badge_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_id")
    private BadgeEntity badge;

    @Builder
    public MemberBadge(MemberEntity member, BadgeEntity badge) {
        setMember(member);
        this.badge = badge;
    }

    private void setMember(MemberEntity member) {
        if (nonNull(this.member)) {
            this.member.getBadges().remove(this);
        }
        this.member = member;
        member.getBadges().add(this);
    }
}
