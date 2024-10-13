package com.smeem.notification.firebase.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.IOException;

import lombok.*;

@Configuration
@EnableConfigurationProperties(FcmProperties.class)
public class FcmConfig {
    private final ClassPathResource firebaseResource;
    private final String projectId;

    public FcmConfig(FcmProperties fcmProperties) {
        this.firebaseResource = new ClassPathResource(fcmProperties.fcm().file_path());
        this.projectId = fcmProperties.fcm().project_id();
    }

    @PostConstruct
    public void init() throws IOException {
        val option = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(firebaseResource.getInputStream()))
                .setProjectId(projectId)
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(option);
        }
    }

    @Bean
    FirebaseMessaging firebaseMessaging() {
        return FirebaseMessaging.getInstance(firebaseApp());
    }

    @Bean
    FirebaseApp firebaseApp() {
        return FirebaseApp.getInstance();
    }
}
