package com.smeem.domain.persistence.entity;

import static com.smeem.common.config.ValueConfig.DEFAULT_IS_PUBLIC_VALUE;
import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PROTECTED;

import com.smeem.api.domain.member.LangType;
import com.smeem.domain.common.BaseTimeEntity;
import com.smeem.domain.topic.model.Topic;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class DiaryEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private LangType targetLang;

    private boolean isPublic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Builder
    public DiaryEntity(String content, Topic topic, MemberEntity member) {
        this.content = content;
        this.targetLang = member.getTargetLang();
        this.isPublic = DEFAULT_IS_PUBLIC_VALUE;
        this.topic = topic;
        setMember(member);
    }

    @Builder
    public DiaryEntity(String content, MemberEntity member) {
        this.content = content;
        this.targetLang = member.getTargetLang();
        this.isPublic = DEFAULT_IS_PUBLIC_VALUE;
        setMember(member);
    }

    public DiaryEntity(Long id, String content, LangType targetLang, boolean isPublic) {
        this.id = id;
        this.content = content;
        this.targetLang = targetLang;
        this.isPublic = isPublic;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void deleteFromMember() {
        if (nonNull(this.member)) {
            this.member.getDiaries().remove(this);
        }
    }

    public boolean isCreatedAt(LocalDate date) {
        return this.createdAt.toLocalDate().equals(date);
    }

    public boolean isBetween(LocalDate startDate, LocalDate endDate) {
        val createdDate = this.createdAt.toLocalDate();
        return createdDate.equals(startDate)
                || (createdDate.isAfter(startDate) && createdDate.isBefore(endDate))
                || createdDate.equals(endDate);
    }

    private void setMember(MemberEntity member) {
        if (nonNull(this.member)) {
            this.member.getDiaries().remove(this);
        }
        this.member = member;
        member.getDiaries().add(this);
    }
}
