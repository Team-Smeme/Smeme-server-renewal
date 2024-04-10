package com.smeem.domain.member.model;

import static java.util.Objects.*;

import com.smeem.domain.badge.model.Badge;

import com.smeem.domain.common.BaseTimeEntity;
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
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_id")
    private Badge badge;

    @Builder
    public MemberBadge(Member member, Badge badge) {
        setMember(member);
        this.badge = badge;
    }

    private void setMember(Member member) {
        if (nonNull(this.member)) {
            this.member.getBadges().remove(this);
        }
        this.member = member;
        member.getBadges().add(this);
    }
}
