package com.smeem.domain.diary.model;

import static java.util.Objects.nonNull;

import com.smeem.domain.member.model.LangType;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.common.BaseTimeEntity;
import com.smeem.domain.topic.model.Topic;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
public class Diary extends BaseTimeEntity {

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
    private Member member;

    @Builder
    public Diary(String content, Topic topic, Member member) {
        this.content = content;
        this.targetLang = member.getTargetLang();
        this.isPublic = true;
        this.topic = topic;
        setMember(member);
    }

    @Builder
    public Diary(String content, Member member) {
        this.content = content;
        this.targetLang = member.getTargetLang();
        this.isPublic = true;
        setMember(member);
    }

    private void setMember(Member member) {
        if (nonNull(this.member)) {
            this.member.getDiaries().remove(this);
        }
        this.member = member;
        member.getDiaries().add(this);
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void deleteFromMember() {
        if (nonNull(this.member)) {
            this.member.getDiaries().remove(this);
            this.member.updateDiaryCombo();
        }
    }

    public boolean isCreatedAt(LocalDate date) {
        return this.createdAt.toLocalDate().equals(date);
    }

    public boolean isWrittenToday() {
        return this.createdAt.toLocalDate().equals(LocalDate.now());
    }

    public boolean isWrittenYesterday() {
        val yesterday = LocalDate.now().minusDays(1);
        return this.createdAt.toLocalDate().equals(yesterday);
    }

    public boolean isBetween(LocalDate startDate, LocalDate endDate) {
        val createdDate = this.createdAt.toLocalDate();
        return createdDate.equals(startDate)
                || (createdDate.isAfter(startDate) && createdDate.isBefore(endDate))
                || createdDate.equals(endDate);
    }

    public boolean isCombo() {
        return this.member.getDiaries().stream().anyMatch(Diary::isWrittenYesterday);
    }
}
