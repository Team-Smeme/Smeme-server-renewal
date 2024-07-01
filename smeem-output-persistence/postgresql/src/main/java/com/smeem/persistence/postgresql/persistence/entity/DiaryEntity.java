package com.smeem.persistence.postgresql.persistence.entity;

import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PROTECTED;

import com.smeem.application.domain.generic.LangType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class DiaryEntity extends BaseEntity {

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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "topic_id")
//    private Topic topic;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private MemberEntity member;
    private long memberId;

}
