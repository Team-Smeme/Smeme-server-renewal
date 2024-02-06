package com.smeem.domain.topic.model;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
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
