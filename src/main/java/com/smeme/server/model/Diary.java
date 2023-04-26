package com.smeme.server.model;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smeme.server.model.topic.Topic;

@Entity
@NoArgsConstructor
@Getter
public class Diary {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private LangType targetLang;

    private boolean isPublic;

    private boolean isDeleted;

    @JsonFormat(timezone = "Asia/Seoul")
    @CreatedDate
    private LocalDateTime createdAt;

    @JsonFormat(timezone = "Asia/Seoul")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "diary")
    private final List<Correction> corrections = new ArrayList<>();

    public Diary(String content, Topic topic, Member member) {
        this.content = content;
        this.targetLang = member.getTargetLang();
        this.isPublic = true;
        this.isDeleted = false;
        this.topic = topic;
        setMember(member);
    }

    private void setMember(Member member) {
        if (Objects.nonNull(this.member)) {
            this.member.getDiaries().remove(this);
        }
        this.member = member;
        member.getDiaries().add(this);
    }
}
