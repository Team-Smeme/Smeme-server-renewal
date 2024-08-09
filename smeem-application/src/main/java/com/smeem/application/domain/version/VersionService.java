package com.smeem.application.domain.version;

import com.smeem.application.port.input.VersionUseCase;
import com.smeem.application.port.input.dto.response.version.RetrieveAppVersionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VersionService implements VersionUseCase {
    private final Property property;

    @Override
    public RetrieveAppVersionResponse retrieveAppVersion() {
        return RetrieveAppVersionResponse.of(
                property.getCLIENT_VERSION_UPDATE_TITLE(),
                property.getCLIENT_VERSION_UPDATE_CONTENT(),
                property.getCLIENT_VERSION_ANDROID_VERSION(),
                property.getCLIENT_VERSION_ANDROID_FORCE_VERSION(),
                property.getCLIENT_VERSION_IOS_VERSION(),
                property.getCLIENT_VERSION_IOS_FORCE_VERSION());
    }
}
