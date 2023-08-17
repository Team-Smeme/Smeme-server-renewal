package com.smeme.server.model.topic;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Topic {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "topic_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    private String content;
}
