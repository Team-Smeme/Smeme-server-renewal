package com.smeem.api.version.service.dto.response;

import com.smeem.common.config.ValueConfig;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record ClientVersionGetServiceResponse(
        String title,
        String content,
        String iosVersion,
        String iosForceVersion,
        String androidVersion,
        String androidForceVersion
) {

    public static ClientVersionGetServiceResponse of(ValueConfig valueConfig) {
        return ClientVersionGetServiceResponse.builder()
                .title(valueConfig.getCLIENT_VERSION_UPDATE_TITLE())
                .content(valueConfig.getCLIENT_VERSION_UPDATE_CONTENT())
                .iosVersion(valueConfig.getCLIENT_VERSION_IOS_VERSION())
                .iosForceVersion(valueConfig.getCLIENT_VERSION_IOS_FORCE_VERSION())
                .androidVersion(valueConfig.getCLIENT_VERSION_ANDROID_VERSION())
                .androidVersion(valueConfig.getCLIENT_VERSION_ANDROID_FORCE_VERSION())
                .build();
    }
}
