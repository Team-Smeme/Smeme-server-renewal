package com.smeem.persistence.postgresql.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.smeem.persistence.postgresql"})
@EntityScan(basePackages = {"com.smeem.persistence.postgresql"})
public class JPAConfig {
}
