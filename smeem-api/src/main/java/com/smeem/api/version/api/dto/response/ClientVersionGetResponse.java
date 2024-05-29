package com.smeem.api.version.api.dto.response;

import com.smeem.api.version.service.dto.response.ClientVersionGetServiceResponse;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record ClientVersionGetResponse(
        String title,
        String content,
        VersionResponse iosVersion,
        VersionResponse androidVersion
) {

    public static ClientVersionGetResponse of(ClientVersionGetServiceResponse response) {
        return ClientVersionGetResponse.builder()
                .title(response.title())
                .content(response.content())
                .iosVersion(VersionResponse.of(response.iosVersion(), response.iosForceVersion()))
                .androidVersion(VersionResponse.of(response.androidVersion(), response.androidForceVersion()))
                .build();
    }

    @Builder(access = PRIVATE)
    private record VersionResponse(
            String version,
            String forceVersion
    ) {

        private static VersionResponse of(String version, String forceVersion) {
            return VersionResponse.builder()
                    .version(version)
                    .forceVersion(forceVersion)
                    .build();
        }
    }
}
