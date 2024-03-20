package com.smeem.api.version.api;

import com.smeem.api.common.ApiResponseUtil;
import com.smeem.api.common.dto.SuccessResponse;
import com.smeem.api.version.api.dto.response.ClientVersionGetResponse;
import com.smeem.api.version.service.VersionService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.smeem.common.code.success.VersionSuccessCode.SUCCESS_GET_APP_VERSION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/versions")
public class VersionApiController implements VersionApi {

    private final VersionService versionService;

    @GetMapping("/client/app")
    public ResponseEntity<SuccessResponse<?>> getClientVersion() {
        val response = ClientVersionGetResponse.of(versionService.getClientVersion());
        return ApiResponseUtil.success(SUCCESS_GET_APP_VERSION, response);
    }
}
