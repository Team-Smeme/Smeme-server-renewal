package com.smeme.server.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "scraps")
@NoArgsConstructor
@Getter
public class Scrap {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", referencedColumnName = "id", nullable = false)
    private Diary diary;

    @Lob
    private String paragraph;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public void setUser(User user) {
        if (Objects.nonNull(this.user)) {
            this.user.getScraps().remove(this);
        }

        this.user = user;
        user.getScraps().add(this);
    }

    public void setDiary(Diary diary) {
        if (Objects.nonNull(this.diary)) {
            this.diary.getScraps().remove(this);
        }

        this.diary = diary;
        diary.getScraps().add(this);
    }
}
