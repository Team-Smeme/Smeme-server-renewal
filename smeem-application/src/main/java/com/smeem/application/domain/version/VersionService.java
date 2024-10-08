package com.smeem.application.domain.version;

import com.smeem.application.config.SmeemProperties;
import com.smeem.application.port.input.VersionUseCase;
import com.smeem.application.port.input.dto.response.version.RetrieveAppVersionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VersionService implements VersionUseCase {
    private final SmeemProperties smeemProperties;

    @Override
    public RetrieveAppVersionResponse retrieveAppVersion() {
        return RetrieveAppVersionResponse.of(
                smeemProperties.getClient().version().title(),
                smeemProperties.getClient().version().content(),
                smeemProperties.getClient().version().android().app(),
                smeemProperties.getClient().version().android().force(),
                smeemProperties.getClient().version().ios().app(),
                smeemProperties.getClient().version().ios().force());
    }
}
