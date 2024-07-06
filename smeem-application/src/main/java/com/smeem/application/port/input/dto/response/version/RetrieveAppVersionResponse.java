package com.smeem.application.port.input.dto.response.version;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record RetrieveAppVersionResponse(
        @Schema(description = "버전 안내 제목")
        String title,
        @Schema(description = "버전 안내 내용")
        String content,
        @Schema(description = "iOS 버전 정보")
        VersionResponse iosVersion,
        @Schema(description = "Android 버전 정보")
        VersionResponse androidVersion
) {

    public static RetrieveAppVersionResponse of(
            String title,
            String content,
            String androidVersion,
            String androidForceVersion,
            String iOSVersion,
            String iOSForceVersion
    ) {
        return RetrieveAppVersionResponse.builder()
                .title(title)
                .content(content)
                .iosVersion(VersionResponse.of(iOSVersion, iOSForceVersion))
                .androidVersion(VersionResponse.of(androidVersion, androidForceVersion))
                .build();
    }

    @Builder(access = AccessLevel.PRIVATE)
    private record VersionResponse(
            @Schema(description = "최신 버전")
            String version,
            @Schema(description = "강제 업데이트 버전")
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
