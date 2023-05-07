package com.smeme.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

@Configuration
public class JpaQueryFactoryConfig {

	@Bean
	JPAQueryFactory jpaQueryFactory(EntityManager em) {
		return new JPAQueryFactory(em);
	}
}
