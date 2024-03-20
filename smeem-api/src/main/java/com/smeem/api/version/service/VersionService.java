package com.smeem.api.version.service;

import com.smeem.api.version.service.dto.response.ClientVersionGetServiceResponse;
import com.smeem.common.config.ValueConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VersionService {

    private final ValueConfig valueConfig;

    public ClientVersionGetServiceResponse getClientVersion() {
        return ClientVersionGetServiceResponse.of(valueConfig);
    }
}
