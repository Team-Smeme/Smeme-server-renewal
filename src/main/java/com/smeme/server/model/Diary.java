package com.smeme.server.model;

import static com.smeme.server.util.Util.getStartOfDay;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static java.util.Objects.nonNull;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.smeme.server.model.topic.Topic;

@Entity
@NoArgsConstructor
@Getter
public class Diary extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private LangType targetLang;

    private boolean isPublic;

    private boolean isDeleted;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "diary")
    private final List<Correction> corrections = new ArrayList<>();

    @Builder
    public Diary(String content, Topic topic, Member member) {
        this.content = content;
        this.targetLang = member.getTargetLang();
        this.isPublic = true;
        this.isDeleted = false;
        this.topic = topic;
        setMember(member);
        this.createdAt = LocalDateTime.now();
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void delete() {
        this.isDeleted = true;
        deleteFromMember();
        this.member.updateDiaryCombo();
    }

    public void deleteFromMember() {
        if (nonNull(this.member)) {
            this.member.getDiaries().remove(this);
        }
    }

    public boolean isValid() {
        return !this.isDeleted;
    }

    public boolean isCreatedAt(LocalDateTime createdAt) {
        return getStartOfDay(this.createdAt).isEqual(getStartOfDay(createdAt));
    }

    public boolean isBetween(LocalDateTime startAt, LocalDateTime endAt) {
        LocalDateTime createdAt = getStartOfDay(this.createdAt);
        startAt = getStartOfDay(startAt);
        endAt = getStartOfDay(endAt);
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
