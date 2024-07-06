package com.smeem.application.port.input;

import com.smeem.application.port.input.dto.response.version.RetrieveAppVersionResponse;

public interface VersionUseCase {
    RetrieveAppVersionResponse retrieveAppVersion();
}
