package com.smeem.oauth.apple.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AppleProperties.class)
public class AppleConfig {
}
