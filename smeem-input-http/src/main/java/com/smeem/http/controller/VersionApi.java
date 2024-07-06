package com.smeem.http.controller;

import com.smeem.application.domain.generic.SmeemMessage;
import com.smeem.application.port.input.VersionUseCase;
import com.smeem.application.port.input.dto.response.SmeemResponse;
import com.smeem.application.port.input.dto.response.version.RetrieveAppVersionResponse;
import com.smeem.http.controller.docs.VersionApiDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/versions")
public class VersionApi implements VersionApiDocs {
    private final VersionUseCase versionUseCase;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/client/app")
    public SmeemResponse<RetrieveAppVersionResponse> retrieveAppVersion() {
        return SmeemResponse.of(
                versionUseCase.retrieveAppVersion(),
                SmeemMessage.RETRIEVE_VERSION);
    }
}
