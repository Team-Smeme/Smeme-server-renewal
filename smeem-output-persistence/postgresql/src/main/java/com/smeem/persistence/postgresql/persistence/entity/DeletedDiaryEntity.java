package com.smeem.persistence.postgresql.persistence.entity;

import com.smeem.application.domain.generic.LangType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class DeletedDiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private LangType targetLang;
    private Long topicId;
    private long memberId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DeletedDiaryEntity(DiaryEntity diary) {
        this.content = diary.getContent();
        this.targetLang = diary.getTargetLang();
        this.topicId = diary.getTopicId();
        this.memberId = diary.getMemberId();
        this.createdAt = diary.getCreatedAt();
        this.updatedAt = diary.getUpdatedAt();
    }
}
