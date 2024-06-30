package com.smeem.http.controller.docs;

import com.smeem.application.port.input.dto.response.SmeemResponse;
import com.smeem.application.port.input.dto.response.topic.RetrieveRandomTopicResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "TopicApi", description = "일기 주제(Topic) 관련 Api 입니다.")
public interface TopicApiDocs {

    @Operation(summary = "랜덤 주제 조회 api", description = "일기 주제를 랜덤으로 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK success",
                    content = @Content(schema = @Schema(implementation = RetrieveRandomTopicResponse.class)))
    })
    SmeemResponse<RetrieveRandomTopicResponse> retrieveTopicByRandom();
}
