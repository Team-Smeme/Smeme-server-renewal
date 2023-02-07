package com.smeme.server.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "likes")
@NoArgsConstructor
@Getter
public class Like {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", referencedColumnName = "id", nullable = false)
    private Diary diary;

    @Builder
    public Like(User user, Diary diary) {
        if (Objects.nonNull(this.user)) {
            this.user.getLikes().remove(this);
        }

        if (Objects.nonNull(this.diary)) {
            this.diary.getLikes().remove(this);
        }

        this.user = user;
        this.diary = diary;

        user.getLikes().add(this);
        diary.getLikes().add(this);
    }
}
