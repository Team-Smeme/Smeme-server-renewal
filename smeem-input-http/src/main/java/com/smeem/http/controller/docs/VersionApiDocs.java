package com.smeem.http.controller.docs;

import com.smeem.http.controller.dto.SmeemResponse;
import com.smeem.application.port.input.dto.response.version.RetrieveAppVersionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "VersionApi", description = "버전(Version) 관련 Api 입니다.")
public interface VersionApiDocs {

    @Operation(summary = "모바일 버전 api", description = "모바일의 최신 버전을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK success",
                    content = @Content(schema = @Schema(implementation = RetrieveAppVersionResponse.class)))
    })
    SmeemResponse<RetrieveAppVersionResponse> retrieveAppVersion();
}
