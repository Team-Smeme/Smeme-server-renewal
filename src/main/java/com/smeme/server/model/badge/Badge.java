package com.smeme.server.model.badge;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Badge {

	@Id @GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Enumerated(value = EnumType.STRING)
	private BadgeType type;

	private String name;

	private String imageUrl;

	public Badge(BadgeType type, String name, String imageUrl) {
		this.type = type;
		this.name = name;
		this.imageUrl = imageUrl;
	}
}
