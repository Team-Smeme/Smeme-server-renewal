package com.smeem.diary.model;

import com.smeme.server.model.topic.Topic;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
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
    private Member member;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public DeletedDiary(Diary diary) {
        this.content = diary.getContent();
        this.targetLang = diary.getTargetLang();
        this.isPublic = diary.isPublic();
        this.topic = diary.getTopic();
        this.member = diary.getMember();
        this.createdAt = diary.getCreatedAt();
        this.updatedAt = LocalDateTime.now();
    }
}
