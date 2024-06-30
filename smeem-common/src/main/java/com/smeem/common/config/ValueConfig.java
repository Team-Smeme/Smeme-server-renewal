//package com.smeem.common.config;
//
//import jakarta.annotation.PostConstruct;
//import lombok.Getter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//
//@Getter
//@Configuration
//public class ValueConfig { //TODO: move personal
//
//    @Value("${smeem.notification.title}")
//    private String NOTIFICATION_TITLE;
//
//    @Value("${smeem.notification.body}")
//    private String NOTIFICATION_BODY;
//
//    @Value("${jwt.APPLE_URL}")
//    private String APPLE_URL;
//
//    @Value("${WELCOME_BADGE_ID}")
//    private Long WELCOME_BADGE_ID;
//
//    @Value("${jwt.KAKAO_URL}")
//    private String KAKAO_URL;
//
//    @Value("${fcm.file_path}")
//    private String FIREBASE_CONFIG_PATH;
//
//    @Value("${fcm.url}")
//    private String FIREBASE_API_URI;
//
//    @Value("${fcm.google_api}")
//    private String GOOGLE_API_URI;
//
//    @Value("${smeem.duration.expired}")
//    private int DURATION_EXPIRED;
//
//    @Value("${smeem.duration.remind}")
//    private int DURATION_REMIND;
//
//    @Value("${discord.webhook.error-url}")
//    private String DISCORD_WEBHOOK_ERROR_URL;
//
//    @Value("${discord.webhook.info-url}")
//    private String DISCORD_WEBHOOK_INFO_URL;
//
//    @Value("${smeem.client.version.title}")
//    private String CLIENT_VERSION_UPDATE_TITLE;
//
//    @Value("${smeem.client.version.content}")
//    private String CLIENT_VERSION_UPDATE_CONTENT;
//
//    @Value("${smeem.client.version.ios.app}")
//    private String CLIENT_VERSION_IOS_VERSION;
//
//    @Value("${smeem.client.version.ios.force}")
//    private String CLIENT_VERSION_IOS_FORCE_VERSION;
//
//    @Value("${smeem.client.version.android.app}")
//    private String CLIENT_VERSION_ANDROID_VERSION;
//
//    @Value("${smeem.client.version.android.force}")
//    private String CLIENT_VERSION_ANDROID_FORCE_VERSION;
//
//    public static final String SIGN_IN_MESSAGE = "새로운 유저 %s 가 가입했습니다! ✍️ \n 현재 회원 수: %d 명";
//
//    public static final boolean DEFAULT_IS_PUBLIC_VALUE = true;
//

//
//
//}
