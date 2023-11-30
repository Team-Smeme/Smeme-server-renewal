package com.smeme.server.model.badge;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Badge {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private BadgeType type;

    private String name;

    private String imageUrl;


    @Builder
    public Badge(Long id, BadgeType type, String name, String imageUrl) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
