package com.smeem.domain.topic.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    private String content;

    @Builder
    public Topic(Category category, String content) {
        this.category = category;
        this.content = content;
    }
}
