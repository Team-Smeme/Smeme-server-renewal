package com.smeme.server.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "diaries")
@NoArgsConstructor
@Getter
public class Diary {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", referencedColumnName = "id", nullable = false)
    private Topic topic;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "target_lang", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TargetLang targetLang;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "diary")
    private List<History> histories = new ArrayList<>();

    @OneToMany(mappedBy = "diary")
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "diary")
    private List<Scrap> scraps = new ArrayList<>();

    @Builder
    public Diary(User user, Topic topic, String content, TargetLang targetLang, boolean isPublic) {
        this.user = user;
        this.topic = topic;
        this.content = content;
        this.targetLang = targetLang;
        this.isPublic = isPublic;
        this.createdAt = LocalDateTime.now();
    }

    public void setUser(User user) {
        if (Objects.nonNull(this.user)) {
            this.user.getDiaries().remove(this);
        }

        this.user = user;
        user.getDiaries().add(this);
    }

    public void setTopic(Topic topic) {
        if (Objects.nonNull(this.topic)) {
            this.topic.getDiaries().remove(this);
        }

        this.topic = topic;
        topic.getDiaries().add(this);
    }

}
