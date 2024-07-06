package com.smeem.application.domain.version;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class Property {
    @Value("${smeem.client.version.title}")
    private String CLIENT_VERSION_UPDATE_TITLE;
    @Value("${smeem.client.version.content}")
    private String CLIENT_VERSION_UPDATE_CONTENT;
    @Value("${smeem.client.version.ios.app}")
    private String CLIENT_VERSION_IOS_VERSION;
    @Value("${smeem.client.version.ios.force}")
    private String CLIENT_VERSION_IOS_FORCE_VERSION;
    @Value("${smeem.client.version.android.app}")
    private String CLIENT_VERSION_ANDROID_VERSION;
    @Value("${smeem.client.version.android.force}")
    private String CLIENT_VERSION_ANDROID_FORCE_VERSION;
}
