package com.smeem.persistence.postgresql.persistence.entity;

import static lombok.AccessLevel.PROTECTED;

import com.smeem.application.domain.generic.LangType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class DiaryEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private LangType targetLang;
    private Long topic;
    @Column(nullable = false)
    private long memberId;
}
