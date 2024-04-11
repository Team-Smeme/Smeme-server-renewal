package com.smeem.domain.support;

import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Component
@Retention(RUNTIME)
@Target(TYPE)
public @interface RepositoryAdapter {
}
