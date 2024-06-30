package com.smeem.domain.visit.model;

import com.smeem.domain.persistence.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    private LocalDateTime visitedAt;

    @Builder
    public Visit(MemberEntity member) {
        this.member = member;
        this.visitedAt = LocalDateTime.now();
    }
}
