package com.smeem.member.model;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static java.util.Objects.*;

import com.smeem.badge.model.Badge;
import com.smeme.server.model.BaseTimeEntity;
import com.smeme.server.model.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
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
