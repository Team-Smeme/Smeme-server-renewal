package com.smeme.server.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "social", length = 20, nullable = false)
    private String social;

    @Column(name = "social_id", nullable = false)
    private String socialId;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "username", length = 50, unique = true)
    private String username;

    @Column(name = "bio")
    private String bio;

    @Column(name = "target_lang", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TargetLang targetLang;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user")
    private List<Diary> diaries = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<History> histories = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Scrap> scraps = new ArrayList<>();
}
