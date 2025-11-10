package com.smeem.http.controller.docs;

import com.smeem.application.port.input.bookmark.dto.*;
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
import org.springframework.web.bind.annotation.PathVariable;

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

    @Operation(summary = "북마크 상세 조회", description = "북마크 ID로 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK success",
                    content = @Content(schema = @Schema(implementation = BookmarkDetailResponse.class))
            )
    })
    SmeemResponse<BookmarkDetailResponse> getBookmarkDetail(
            @Parameter(hidden = true) @MemberId long memberId,
            @Parameter(description = "조회할 북마크 ID", required = true) @PathVariable long bookmarkId
    );

    @Operation(summary = "북마크 전체 조회", description = "사용자가 등록한 북마크 전체를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK success",
                    content = @Content(schema = @Schema(implementation = BookmarkListResponse.class))
            )
    })
    SmeemResponse<BookmarkListResponse> getBookmarks(
            @Parameter(hidden = true) @MemberId long memberId
    );

    @Operation(summary = "북마크 삭제", description = "북마크 ID를 기반으로 북마크를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK success"
            )
    })
    SmeemResponse<?> deleteBookmark(
            @Parameter(hidden = true) @MemberId long memberId,
            @Parameter(description = "삭제할 북마크 ID", required = true) @PathVariable long bookmarkId
    );

    @Operation(summary = "북마크 수정", description = "북마크 ID를 기반으로 북마크 내용을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK success",
                    content = @Content(schema = @Schema(implementation = BookmarkModifyResponse.class))
            )
    })
    SmeemResponse<BookmarkModifyResponse> modifyBookmark(
            @Parameter(hidden = true) @MemberId long memberId,
            @Parameter(description = "수정할 북마크 ID", required = true) @PathVariable long bookmarkId,
            @RequestBody(
                    description = "북마크 수정 요청",
                    required = true,
                    content = @Content(schema = @Schema(implementation = BookmarkUpdateRequest.class))
            ) BookmarkUpdateRequest request
    );
}
