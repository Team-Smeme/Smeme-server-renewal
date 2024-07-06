package com.smeem.persistence.postgresql.persistence.entity;

import com.smeem.application.domain.diary.Diary;
import com.smeem.application.domain.generic.LangType;
import com.smeem.application.domain.member.Member;
import com.smeem.application.domain.topic.Topic;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "diary", schema = "smeem")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DiaryEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private LangType targetLang;
    private Long topicId;
    @Column(nullable = false)
    private long memberId;

    public static DiaryEntity of(Diary diary) {
        return DiaryEntity.builder()
                .content(diary.getContent())
                .targetLang(diary.getTargetLang())
                .topicId(diary.getTopic() != null ? diary.getTopic().getId() : null)
                .memberId(diary.getMember().getId())
                .build();
    }

    public Diary toDomain() {
        return Diary.builder()
                .id(id)
                .content(content)
                .targetLang(targetLang)
                .createdAt(createdAt)
                .build();
    }

    public Diary toDomain(Member member, Topic topic) {
        return Diary.builder()
                .id(id)
                .content(content)
                .targetLang(targetLang)
                .createdAt(createdAt)
                .member(member)
                .topic(topic)
                .build();
    }

    public DiaryEntity update(Diary diary) {
        this.content = diary.getContent();
        this.topicId = diary.getTopic() != null ? diary.getTopic().getId() : null;
        return this;
    }
}
