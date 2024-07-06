package com.smeem.http.controller.docs;

import com.smeem.http.controller.dto.SmeemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.security.Principal;

@Tag(name = "TestApi", description = "테스트(Test) 관련 Api 입니다.")
public interface TestApiDocs {

    @Operation(summary = "푸시알림 api", description = "푸시알림을 테스트합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "NO CONTENT success")
    })
    SmeemResponse<?> pushAlarm(@Parameter(hidden = true) Principal principal);
}
