package com.smeme.server.model.badge;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Badge {

	@Id @GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Enumerated(value = EnumType.STRING)
	private BadgeType type;

	private String name;

	private String imageUrl;
}
