package com.smeme.server.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Getter
public class Category {

    @Id @GeneratedValue
    @Column
    private Long id;

    @Column(name = "content", length = 20, nullable = false, unique = true)
    private String content;

    @OneToMany(mappedBy = "category")
    private List<Topic> topics = new ArrayList<>();
}
