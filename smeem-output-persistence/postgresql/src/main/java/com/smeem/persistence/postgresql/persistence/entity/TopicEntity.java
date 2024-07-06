package com.smeem.persistence.postgresql.persistence.entity;

import com.smeem.application.domain.topic.Topic;
import com.smeem.application.domain.topic.TopicCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "topic", schema = "smeem")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class TopicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "category", nullable = false)
    private TopicCategory topicCategory;
    @Column(nullable = false)
    private String content;

    public Topic toDomain() {
        return Topic.builder()
                .id(id)
                .topicCategory(topicCategory)
                .content(content)
                .build();
    }
}
