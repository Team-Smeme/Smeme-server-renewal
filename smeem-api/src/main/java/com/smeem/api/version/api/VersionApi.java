package com.smeem.api.version.api;

import com.smeem.api.common.dto.FailureResponse;
import com.smeem.api.common.dto.SuccessResponse;
import com.smeem.api.version.api.dto.response.ClientVersionGetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "[Version] 버 관련 API (V2)")
public interface VersionApi {

    @Operation(summary = "클라이언트 앱 버전 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content(schema = @Schema(implementation = FailureResponse.class))
            )
    })
    ResponseEntity<SuccessResponse<ClientVersionGetResponse>> getClientVersion();
}
