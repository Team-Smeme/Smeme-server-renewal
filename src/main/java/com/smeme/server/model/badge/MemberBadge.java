package com.smeme.server.model.badge;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static java.util.Objects.*;

import com.smeme.server.model.BaseTimeEntity;
import com.smeme.server.model.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class MemberBadge extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_badge_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "badge_id")
    private Badge badge;

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
