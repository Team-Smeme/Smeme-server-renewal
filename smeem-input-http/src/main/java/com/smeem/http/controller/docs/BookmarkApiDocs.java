package com.smeem.http.controller.docs;

import com.smeem.application.port.input.dto.request.bookmark.BookmarkFallbackRequest;
import com.smeem.application.port.input.dto.request.bookmark.BookmarkRequest;
import com.smeem.application.port.input.dto.response.bookmark.BookmarkResponse;
import com.smeem.http.controller.dto.SmeemResponse;
import com.smeem.http.security.MemberId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Bookmark API", description = "북마크 API")
public interface BookmarkApiDocs {

    @Operation(summary = "북마크 등록", description = "URL을 스크랩한 결과를 기반으로 북마크를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "CREATED success",
                    content = @Content(schema = @Schema(implementation = BookmarkResponse.class))
            )
    })
    SmeemResponse<BookmarkResponse> bookmark(
            @Parameter(hidden = true) @MemberId long memberId,
            @RequestBody(
                    description = "북마크 등록 요청",
                    required = true,
                    content = @Content(schema = @Schema(implementation = BookmarkRequest.class))
            ) BookmarkRequest request
    );

    @Operation(summary = "북마크 등록 (Fallback)", description = "썸네일 정보 추출이 실패한 경우 북마크를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "CREATED success",
                    content = @Content(schema = @Schema(implementation = BookmarkResponse.class))
            )
    })
    SmeemResponse<BookmarkResponse> bookmarkFallback(
            @Parameter(hidden = true) @MemberId long memberId,
            @RequestBody(
                    description = "북마크 fallback 등록 요청",
                    required = true,
                    content = @Content(schema = @Schema(implementation = BookmarkFallbackRequest.class))
            ) BookmarkFallbackRequest request
    );
}
