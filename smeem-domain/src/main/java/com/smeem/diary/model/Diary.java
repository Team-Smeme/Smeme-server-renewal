package com.smeem.diary.model;

import static com.smeme.server.util.Util.getStartOfDay;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static java.util.Objects.nonNull;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.smeme.server.model.topic.Topic;

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
        this.createdAt = LocalDateTime.now();
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

    public boolean isCreatedAt(LocalDateTime createdAt) {
        return Util.getStartOfDay(this.createdAt).isEqual(Util.getStartOfDay(createdAt));
    }

    public boolean isBetween(LocalDateTime startAt, LocalDateTime endAt) {
        LocalDateTime createdAt = Util.getStartOfDay(this.createdAt);
        startAt = Util.getStartOfDay(startAt);
        endAt = Util.getStartOfDay(endAt);
        return createdAt.equals(startAt) || (createdAt.isAfter(startAt) && createdAt.isBefore(endAt)) || createdAt.equals(endAt);
    }

    private void setMember(Member member) {
        if (nonNull(this.member)) {
            this.member.getDiaries().remove(this);
        }
        this.member = member;
        member.getDiaries().add(this);
    }
}
