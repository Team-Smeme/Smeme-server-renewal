package com.smeem.domain.diary.model;

import com.smeem.api.domain.member.LangType;
import com.smeem.domain.persistence.entity.DiaryEntity;
import com.smeem.domain.persistence.entity.MemberEntity;
import com.smeem.domain.topic.model.Topic;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class DeletedDiary {
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

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public DeletedDiary(DiaryEntity diary) {
        this.content = diary.getContent();
        this.targetLang = diary.getTargetLang();
        this.isPublic = diary.isPublic();
        this.topic = diary.getTopic();
        this.member = diary.getMember();
        this.createdAt = diary.getCreatedAt();
        this.updatedAt = LocalDateTime.now();
    }
}
