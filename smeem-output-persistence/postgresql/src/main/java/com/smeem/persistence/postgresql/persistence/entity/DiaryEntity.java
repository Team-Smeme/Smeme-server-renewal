package com.smeem.persistence.postgresql.persistence.entity;

import com.smeem.application.domain.diary.Diary;
import com.smeem.application.domain.generic.LangType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "diary", schema = "smeem")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DiaryEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 6000, nullable = false)
    private String content;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private LangType targetLang;
    private Long topicId;
    @Column(nullable = false)
    private long memberId;

    public DiaryEntity(Diary diary) {
        this.content = diary.getContent();
        this.targetLang = diary.getTargetLang();
        this.topicId = diary.getTopicId();
        this.memberId = diary.getMemberId();
    }

    public Diary toDomain() {
        return Diary.builder()
                .id(id)
                .content(content)
                .targetLang(targetLang)
                .topicId(topicId)
                .memberId(memberId)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public DiaryEntity update(Diary diary) {
        this.content = diary.getContent();
        this.topicId = diary.getTopicId();
        return this;
    }
}
