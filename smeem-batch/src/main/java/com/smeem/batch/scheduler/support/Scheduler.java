package com.smeem.batch.scheduler.support;


import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@Component
@EnableScheduling
public @interface Scheduler {
}
